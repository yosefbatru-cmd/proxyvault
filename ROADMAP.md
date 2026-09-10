# ProxyVault — Roadmap

What’s shipping next for users. Dates are targets, not promises.

---

## Now (v1.0 — live)

- Multi-source fetch (GitHub lists + free APIs)
- Concurrent real-time validation
- Dark UI + export to clipboard
- Code activation: Free / Premium / Pro / Lifetime
- Per-tier export limits & validation batch size

---

## Near term (v1.1 – v1.3)

### Better lists
- [ ] Country / city filter (geo tags via free MaxMind / IP2Location)
- [ ] Protocol filter (HTTP only / SOCKS5 only / mixed)
- [ ] Speed bands (fast / medium / any)
- [ ] Anonymity level tags (elite / anonymous / transparent)
- [ ] Search bar (IP, country, source)

### Sources
- [ ] Toggle individual sources on/off in Settings
- [ ] Live crawl status per source
- [ ] Add custom source URL (Pro+)
- [ ] Auto-refresh on schedule (15 min / 1 h / 4 h / 24 h)

### Export
- [ ] Format picker: `IP:PORT` | `http://IP:PORT` | SOCKS5 | cURL one-liners
- [ ] Save to file (Downloads)
- [ ] Share sheet (send list to any app)
- [ ] Bulk export history (last N exports)

### UX
- [ ] Swipe left = copy, swipe right = solo test (with haptic)
- [ ] Pull-to-refresh on list
- [ ] Offline cache of last good list
- [ ] Notification when working count drops under threshold (Premium+)

---

## Mid term (v1.4 – v2.0)

### Analytics (Premium+)
- [ ] Working proxy count over time (24h / 7d / 30d)
- [ ] Source reliability ranking (which feeds stay alive longest)
- [ ] Geographic heatmap (density by country)
- [ ] Export analytics as PDF / CSV

### Account & billing
- [ ] Optional Google Play Billing (alongside code activation)
- [ ] Restore purchase
- [ ] 7-day Premium trial on first install
- [ ] Lifetime one-time SKU

### Quality
- [ ] Latency graph per proxy (last 24h)
- [ ] Uptime % estimate from repeated checks
- [ ] Dead-node auto purge from cache
- [ ] Background validation worker (WorkManager)

---

## Longer term (v2+)

### Power users / Pro
- [ ] Local REST API on device (for scripts / automation)
- [ ] Custom source packs (import JSON of feeds)
- [ ] White-label / rebrand option
- [ ] Desktop companion (optional)

### Platform
- [ ] iOS app (Swift / shared backend)
- [ ] Optional cloud sync of favorites (opt-in only)
- [ ] Affiliate VPN partner links (Express / Nord style) for secondary revenue

### Backend (optional shared service)
- [ ] Hosted validator API with higher concurrency
- [ ] One-time redeem tokens (replace static codes for public sales)
- [ ] Rate-limited free tier endpoint for the app

---

## What we will not do

- Sell user traffic or device fingerprints
- Inject malware or ads that steal data
- Claim “unlimited residential ISP rotation” we don’t operate
- Require an account just to use the free tier

---

**Feedback**  
Open an issue on this repo or contact the maintainer. Feature requests that help real users find *working* proxies fast get priority.
