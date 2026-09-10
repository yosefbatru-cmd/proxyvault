# ProxyVault — Full Launch Kit

## Monetization (copy into billing config)

### Free
- Unlimited viewing
- 50 proxies/day export
- 4-hour refresh
- 7-day retention
- Small ad banner optional

### Premium — $3.99/mo · $29.99/yr
- Unlimited export
- 1-hour auto-refresh
- 30-day retention
- Analytics dashboard
- No ads

### Pro — $9.99/mo · $79.99/yr
- 15-minute refresh
- 90-day retention
- Custom sources
- REST API (1000 req/day)
- Bulk 500K export
- White-label option

### Lifetime Premium
- One-time $49.99

### Trial
- 7 days Premium on first install

## UI Selling Points

**Home**
- Big green REFRESH NOW
- Animated live counter
- Last refresh + source count
- Quick cards: Country · Speed · Export

**List**
- Card: IP:PORT + flag + speed color + uptime
- Swipe left = copy (haptic)
- Swipe right / long-press = solo test
- Search by IP / country / speed

**Sources**
- Per-feed toggle + live crawl status
- Interval selector
- Export format picker

**Analytics (Premium)**
- Proxy count over time
- Source reliability ranking
- Geo heatmap

## Architecture Notes

- Android: Kotlin + ViewBinding + Coroutines + OkHttp
- Validation concurrent, chunked for device safety
- Sources in `SourceCatalog` — easy to extend
- Optional Python FastAPI backend for shared high-concurrency validation
- Cleartext allowed (public proxy lists are often HTTP)

## Legal / Play Store Notes

- Position as research / testing / privacy tooling
- No claim of unlimited residential / ISP rotation
- No malware, no data sale in free tier
- Users responsible for target site ToS
