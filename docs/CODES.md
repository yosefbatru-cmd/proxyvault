# ProxyVault — Activation Codes

After payment (manual / crypto / bank / whatever channel you use), send the buyer **one** of these codes.

They open the app → **Enter code / Upgrade** (or menu → Activate code) → paste → Activate.

## Premium

| # | Code |
|---|------|
| 1 | `premium-mr-unknown` |
| 2 | `mr-premium-unknown` |

## Pro

| # | Code |
|---|------|
| 1 | `pro-mr-unknown` |
| 2 | `pro-unknown-mr` |

## Lifetime

| # | Code |
|---|------|
| 1 | `lifetime-mr-unknown09` |

## Behavior

- Codes are **case-insensitive**, spaces ignored.
- Each code can be redeemed **once per device** (tracked in SharedPreferences).
- Higher tier always wins: Lifetime > Pro > Premium > Free.
- Free export cap: **50** proxies/day-style (per export action).
- Premium+: unlimited export.
- Validation batch size scales with tier (Free 400 / Premium 1200 / Pro+Lifetime 3000).

## How you sell

1. Customer pays you (any method).
2. You message them one unused code for the tier they bought.
3. They redeem in-app. Done.

No Play Billing required for this path. You control distribution of codes.

## Security note

These are **static master codes** baked into the APK. Fine for early launch / private sales.
Later you can swap `LicenseManager` to hit your own backend (`POST /redeem` with one-time tokens) without changing the UI.
