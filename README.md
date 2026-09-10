# ProxyVault — Real-Time Proxy Finder & Validator

**50+ sources. One tap. Live proxies.**

ProxyVault aggregates working proxies from 50+ independent sources worldwide. Every proxy is tested in real-time. Dead nodes are auto-purged. You get working proxies in seconds.

> Built by [SPIRIT] — launch-ready Android app + shared validation core.

## What It Does

- Crawls GitHub lists, public APIs, cache indexes, forum archives, and leak datasets simultaneously
- Validates each proxy (latency, anonymity level, geolocation)
- Ranks working proxies by speed and uptime
- Export to clipboard, file, or any app (HTTP / SOCKS5 / plain / cURL)
- Scheduled auto-refresh (15 min → 24 h)
- Filter by country, speed, anonymity
- Real-time statistics dashboard

## Why You Need It

Free proxy lists are ~40% dead by upload time. Manual testing burns hours. ProxyVault does the entire pipeline in minutes — no manual work, no dead weight.

## Key Features

- Multi-source aggregation (50+ live feeds)
- Sub-second validation per proxy (concurrent)
- Geolocation & ISP tagging
- Bulk export (1K–50K nodes)
- Dark mode UI
- Offline mode (cached proxies)
- Free tier with no ads (premium removes refresh delays)
- Works with VPN apps, browsers, scrapers, tools

## Screens

1. **Home Dashboard** — live working count, REFRESH NOW, quick filters
2. **Proxy List** — card view (IP:PORT, flag, speed, uptime), swipe copy / solo test
3. **Settings & Sources** — toggle feeds, refresh interval, export format
4. **Analytics** (Premium) — count over time, source reliability, geo heatmap
5. **Account & Premium** — Free / Premium / Pro tiers

## Monetization (built-in)

| Tier | Price | Highlights |
|------|-------|------------|
| Free | $0 | View unlimited, 50 export/day, 4-hour refresh |
| Premium | $3.99/mo or $29.99/yr | Unlimited export, 1-hour refresh, analytics |
| Pro | $9.99/mo or $79.99/yr | 15-min refresh, custom sources, API access, bulk 500K |

- 7-day Premium trial on first install
- Lifetime Premium one-time: $49.99
- Affiliate VPN links (optional)

## Data Sources (sample)

**GitHub**
- TheSpeedX/PROXY-List
- ShiftyTR/Proxy-List
- roosterkid/openproxylist
- mertguvencli/http-proxy-list
- monosans / proxifly style feeds

**APIs**
- ProxyScrape free tier
- PubProxy
- Free-Proxy-List.net JSON

**Other**
- Google Cache / archived lists
- Reddit megathreads (parsed)
- Pastebin pattern scans
- Public WHOIS / datacenter ranges
- MaxMind GeoIP2 free + IP2Location free for geo tags

## Project Structure

```
proxyvault/
├── app/                          # Android (Kotlin)
│   └── src/main/
│       ├── java/com/spiritdev/proxyvault/
│       │   ├── ui/
│       │   ├── network/
│       │   ├── model/
│       │   └── util/
│       └── res/
├── backend/                      # Optional shared validation service
├── docs/                         # Launch kit, store copy
├── .github/workflows/
└── README.md
```

## Build (Android)

```bash
git clone https://github.com/yosefbatru-cmd/proxyvault.git
cd proxyvault
# Open in Android Studio or:
./gradlew assembleDebug
```

Requires Android SDK 24+, target 34+.

## Backend (optional)

Python FastAPI validator in `backend/`. Run locally or on a VPS for higher concurrency.

```bash
cd backend && pip install -r requirements.txt
python validator_service.py
# GET http://localhost:8080/proxies?limit=100
```

## Store Copy

See `docs/STORE_COPY.md` and `docs/LAUNCH_KIT.md`.

## License

Proprietary / SPIRIT. All rights reserved unless otherwise noted.

## Disclaimer

For legitimate network testing, research, and privacy tooling. Users are responsible for complying with applicable laws and target site terms.

---

**ProxyVault** — Real proxies in seconds. Not hours.
