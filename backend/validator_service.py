#!/usr/bin/env python3
"""
ProxyVault backend validator (optional shared service).
Fast concurrent fetch + validate for higher throughput than pure mobile.
Run: python validator_service.py
"""

from __future__ import annotations

import asyncio
import re
import time
from typing import List, Optional

import aiohttp
from aiohttp import ClientSession, ClientTimeout
from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel

app = FastAPI(title="ProxyVault Validator", version="1.0.0")
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

IP_PORT = re.compile(r"(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}):(\d{2,5})")

SOURCES = [
    "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/http.txt",
    "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt",
    "https://raw.githubusercontent.com/ShiftyTR/Proxy-List/master/http.txt",
    "https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/http.txt",
    "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=http&timeout=10000&country=all",
    "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=socks5&timeout=10000&country=all",
    "https://raw.githubusercontent.com/roosterkid/openproxylist/main/HTTPS_RAW.txt",
    "https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list-raw.txt",
]


class ProxyOut(BaseModel):
    ip: str
    port: int
    protocol: str = "http"
    speed_ms: int
    source: str = ""


async def fetch_source(session: ClientSession, url: str) -> List[str]:
    try:
        async with session.get(url, timeout=ClientTimeout(total=15)) as resp:
            if resp.status != 200:
                return []
            text = await resp.text()
            return [f"{m.group(1)}:{m.group(2)}" for m in IP_PORT.finditer(text)]
    except Exception:
        return []


async def validate_one(session: ClientSession, addr: str, timeout: float = 6.0) -> Optional[ProxyOut]:
    ip, port_s = addr.split(":")
    port = int(port_s)
    proxy_url = f"http://{addr}"
    start = time.perf_counter()
    try:
        async with session.get(
            "http://httpbin.org/ip",
            proxy=proxy_url,
            timeout=ClientTimeout(total=timeout),
        ) as resp:
            if resp.status == 200:
                ms = int((time.perf_counter() - start) * 1000)
                return ProxyOut(ip=ip, port=port, speed_ms=ms)
    except Exception:
        pass
    return None


@app.get("/health")
async def health():
    return {"status": "ok", "service": "ProxyVault"}


@app.get("/proxies", response_model=List[ProxyOut])
async def get_proxies(
    limit: int = Query(100, ge=1, le=2000),
    max_candidates: int = Query(600, ge=50, le=5000),
):
    timeout = ClientTimeout(total=20)
    async with aiohttp.ClientSession(timeout=timeout) as session:
        tasks = [fetch_source(session, u) for u in SOURCES]
        batches = await asyncio.gather(*tasks)
        candidates = list(dict.fromkeys(x for batch in batches for x in batch))[:max_candidates]

        sem = asyncio.Semaphore(40)

        async def guarded(addr: str):
            async with sem:
                return await validate_one(session, addr)

        results = await asyncio.gather(*[guarded(a) for a in candidates])
        alive = [r for r in results if r is not None]
        alive.sort(key=lambda p: p.speed_ms)
        return alive[:limit]


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8080)
