# ProxyVault — Real-Time Proxy Finder & Validator

**50+ sources. One tap. Live proxies.**

ProxyVault aggregates working proxies from public sources worldwide. Every proxy is tested in real-time. Dead nodes are auto-purged. You get working proxies in seconds.

> Built by [SPIRIT] — launch-ready Android app + shared validation core.

## What It Does

- Crawls GitHub lists, public APIs, and free proxy feeds simultaneously
- Validates each proxy (latency, alive check)
- Ranks working proxies by speed
- Export to clipboard (HTTP / plain IP:PORT)
- Code activation for Premium / Pro / Lifetime
- Free tier works fully; paid tiers remove limits

## Why You Need It

Free proxy lists are often ~40% dead by the time you download them. Manual testing burns hours. ProxyVault does the pipeline in minutes.

## Key Features (v1.0)

- Multi-source aggregation
- Concurrent real-time validation
- Dark mode UI
- One-tap refresh + export
- Tier badge (FREE / PREMIUM / PRO / LIFETIME)
- Offline code activation (no store account required)

## Activation codes

After payment, redeem in-app via **Enter code / Upgrade**.

See **[docs/CODES.md](docs/CODES.md)** for the current Premium / Pro / Lifetime codes and limits.

| Tier | Export | Validation batch |
|------|--------|------------------|
| Free | 50 | 400 |
| Premium | unlimited | 1200 |
| Pro / Lifetime | unlimited | 3000 |

## Future for users

We’re not stopping at v1. Planned for you:

**Soon**
- Country / protocol / speed filters
- Source on/off toggles + custom feeds (Pro)
- Export as SOCKS5, cURL, file save, share sheet
- Auto-refresh on a schedule
- Offline cache of last good list

**Next**
- Analytics: count over time, source reliability, geo heatmap
- Optional Play Billing + 7-day trial (codes still work)
- Latency history + uptime estimates
- Background validation

**Later**
- iOS app
- Local device API for scripts
- One-time online redeem tokens
- Optional hosted high-concurrency validator

Full detail: **[ROADMAP.md](ROADMAP.md)**

## Project structure

```
proxyvault/
├── app/                 # Android (Kotlin)
├── backend/             # Optional FastAPI validator
├── docs/                # Store copy, codes, launch kit
├── ROADMAP.md           # Future features
└── README.md
```

## Build

```bash
git clone https://github.com/yosefbatru-cmd/proxyvault.git
cd proxyvault
# Open in Android Studio (generates Gradle wrapper if needed)
# Run on device / emulator
```

Requires Android SDK 24+, target 34+.

## Backend (optional)

```bash
cd backend && pip install -r requirements.txt
python validator_service.py
# GET http://localhost:8080/proxies?limit=100
```

## Store / launch copy

- [docs/STORE_COPY.md](docs/STORE_COPY.md)
- [docs/LAUNCH_KIT.md](docs/LAUNCH_KIT.md)
- [docs/CODES.md](docs/CODES.md)

## Disclaimer

For legitimate network testing, research, and privacy tooling. Users are responsible for complying with applicable laws and target site terms. We do not sell traffic or device data.

---

**ProxyVault** — Real proxies in seconds. Not hours.

📋 Roadmap · 🔑 Codes · 📦 Store copy — all in this repo for you.
