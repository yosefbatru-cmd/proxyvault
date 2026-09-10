package com.spiritdev.proxyvault.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Offline code activation for Premium / Pro / Lifetime.
 * Codes are single-use per device (stored as redeemed).
 *
 * Code format (Axion keys):
 *   Premium:  premium-mr-unknown  |  mr-premium-unknown
 *   Pro:      pro-mr-unknown      |  pro-unknown-mr
 *   Lifetime: lifetime-mr-unknown09
 */
enum class Tier {
    FREE, PREMIUM, PRO, LIFETIME
}

object LicenseManager {

    private const val PREFS = "proxyvault_license"
    private const val KEY_TIER = "tier"
    private const val KEY_CODE = "activated_code"
    private const val KEY_REDEEMED = "redeemed_codes"

    // Master codes (case-insensitive match after normalize)
    private val PREMIUM_CODES = setOf(
        "premium-mr-unknown",
        "mr-premium-unknown"
    )
    private val PRO_CODES = setOf(
        "pro-mr-unknown",
        "pro-unknown-mr"
    )
    private val LIFETIME_CODES = setOf(
        "lifetime-mr-unknown09"
    )

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun currentTier(ctx: Context): Tier {
        val name = prefs(ctx).getString(KEY_TIER, Tier.FREE.name) ?: Tier.FREE.name
        return try {
            Tier.valueOf(name)
        } catch (_: Exception) {
            Tier.FREE
        }
    }

    fun activatedCode(ctx: Context): String? =
        prefs(ctx).getString(KEY_CODE, null)

    fun isPremiumOrHigher(ctx: Context): Boolean {
        val t = currentTier(ctx)
        return t == Tier.PREMIUM || t == Tier.PRO || t == Tier.LIFETIME
    }

    fun isProOrHigher(ctx: Context): Boolean {
        val t = currentTier(ctx)
        return t == Tier.PRO || t == Tier.LIFETIME
    }

    fun isLifetime(ctx: Context): Boolean = currentTier(ctx) == Tier.LIFETIME

    /** Export limit for free tier */
    fun exportLimit(ctx: Context): Int = when (currentTier(ctx)) {
        Tier.FREE -> 50
        Tier.PREMIUM, Tier.PRO, Tier.LIFETIME -> Int.MAX_VALUE
    }

    /** Max candidates to validate per refresh */
    fun validationCap(ctx: Context): Int = when (currentTier(ctx)) {
        Tier.FREE -> 400
        Tier.PREMIUM -> 1200
        Tier.PRO, Tier.LIFETIME -> 3000
    }

    /** Suggested auto-refresh interval minutes (UI only for now) */
    fun refreshIntervalMinutes(ctx: Context): Int = when (currentTier(ctx)) {
        Tier.FREE -> 240
        Tier.PREMIUM -> 60
        Tier.PRO, Tier.LIFETIME -> 15
    }

    data class RedeemResult(
        val ok: Boolean,
        val message: String,
        val tier: Tier = Tier.FREE
    )

    fun redeem(ctx: Context, rawCode: String): RedeemResult {
        val code = normalize(rawCode)
        if (code.isEmpty()) {
            return RedeemResult(false, "Enter a code")
        }

        val redeemed = getRedeemed(ctx)
        if (code in redeemed) {
            return RedeemResult(false, "Code already used on this device")
        }

        val tier = when {
            code in LIFETIME_CODES -> Tier.LIFETIME
            code in PRO_CODES -> Tier.PRO
            code in PREMIUM_CODES -> Tier.PREMIUM
            else -> null
        } ?: return RedeemResult(false, "Invalid code")

        // Lifetime always wins; Pro upgrades Premium; Premium only if Free
        val current = currentTier(ctx)
        val next = maxTier(current, tier)

        prefs(ctx).edit()
            .putString(KEY_TIER, next.name)
            .putString(KEY_CODE, code)
            .putStringSet(KEY_REDEEMED, redeemed + code)
            .apply()

        val label = when (next) {
            Tier.LIFETIME -> "Lifetime"
            Tier.PRO -> "Pro"
            Tier.PREMIUM -> "Premium"
            else -> "Free"
        }
        return RedeemResult(true, "Activated $label", next)
    }

    fun clearLicense(ctx: Context) {
        prefs(ctx).edit()
            .remove(KEY_TIER)
            .remove(KEY_CODE)
            .apply()
    }

    private fun normalize(raw: String): String =
        raw.trim().lowercase().replace(" ", "")

    private fun getRedeemed(ctx: Context): Set<String> =
        prefs(ctx).getStringSet(KEY_REDEEMED, emptySet())?.toSet() ?: emptySet()

    private fun maxTier(a: Tier, b: Tier): Tier {
        val order = listOf(Tier.FREE, Tier.PREMIUM, Tier.PRO, Tier.LIFETIME)
        return if (order.indexOf(a) >= order.indexOf(b)) a else b
    }
}
