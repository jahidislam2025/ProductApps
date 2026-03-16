package com.qsoft.designsystem.utils


import androidx.compose.ui.graphics.Color
import kotlin.math.log2
import kotlin.math.max

enum class PasswordStrength(val score: Int, val label: String, val color: Color) {
    VERY_WEAK(0, "Very weak", Color(0xFFD32F2F)),
    WEAK(1,      "Weak",      Color(0xFFF57C00)),
    FAIR(2,      "Fair",      Color(0xFFFBC02D)),
    STRONG(3,    "Strong",    Color(0xFF388E3C)),
    VERY_STRONG(4,"Very strong", Color(0xFF2E7D32));
}

data class StrengthResult(
    val strength: PasswordStrength,
    val score: Int,
    val entropyBits: Double,
    val advice: String
){
    companion object {
        val EMPTY = StrengthResult(
            strength = PasswordStrength.VERY_WEAK,
            score = 0,
            entropyBits = 0.0,
            advice = "Password must be at least 8 characters long."
        )
    }
}

private val COMMON = setOf(
    "password","123456","123456789","qwerty","111111","123123","12345",
    "12345678","abc123","password1","iloveyou","admin","letmein","welcome",
)

fun calculatePasswordStrength(pw: String): StrengthResult {
    if (pw.length < 8) {
        return StrengthResult(
            PasswordStrength.VERY_WEAK,
            0,
            0.0,
            "Password must be at least 8 characters long."
        )
    }

    val lower = pw.any { it.isLowerCase() }
    val upper = pw.any { it.isUpperCase() }
    val digit = pw.any { it.isDigit() }
    val symbol = pw.any { !it.isLetterOrDigit() }

    val classes = listOf(lower, upper, digit, symbol).count { it }

    // Base score from length and diversity
    var raw = 0.0
    raw += when {
        pw.length >= 16 -> 2.0
        pw.length >= 12 -> 1.5
        pw.length >= 10 -> 1.0
        pw.length >= 8  -> 0.5
        else            -> 0.0 // won't happen now due to early return
    }
    raw += when (classes) {
        4 -> 2.0
        3 -> 1.5
        2 -> 1.0
        1 -> 0.0
        else -> 0.0
    }

    // Penalties
    if (pw.lowercase() in COMMON) raw -= 2.0
    if (hasSimpleSequence(pw)) raw -= 1.0
    if (hasKeyboardWalk(pw)) raw -= 0.5
    raw -= repeatPenalty(pw)

    val clamped = max(0.0, raw)
    val score = when {
        clamped >= 3.5 -> 4
        clamped >= 2.7 -> 3
        clamped >= 1.7 -> 2
        clamped >= 0.8 -> 1
        else -> 0
    }

    val strength = when (score) {
        4 -> PasswordStrength.VERY_STRONG
        3 -> PasswordStrength.STRONG
        2 -> PasswordStrength.FAIR
        1 -> PasswordStrength.WEAK
        else -> PasswordStrength.VERY_WEAK
    }

    val pool = (if (lower) 26 else 0) +
            (if (upper) 26 else 0) +
            (if (digit) 10 else 0) +
            (if (symbol) 33 else 0)
    val entropy = if (pool > 0) pw.length * log2(pool.toDouble()) else 0.0

    val advice = when (strength) {
        PasswordStrength.VERY_WEAK -> "Password must be at least 8 characters with a mix of types."
        PasswordStrength.WEAK      -> "Add more characters and another character type."
        PasswordStrength.FAIR      -> "Increase to 12–16+ characters or add more randomness."
        PasswordStrength.STRONG    -> "Great. Make it longer for very strong."
        PasswordStrength.VERY_STRONG -> "Excellent. Keep it unique (don’t reuse)."
    }

    return StrengthResult(strength, score, entropy, advice)
}


private fun hasSimpleSequence(s: String): Boolean {
    val t = s.lowercase()
    val sequences = listOf(
        "abcdefghijklmnopqrstuvwxyz",
        "qwertyuiopasdfghjklzxcvbnm",
        "0123456789"
    )
    return sequences.any { seq ->
        t.windowed(3, 1).any { w ->
            seq.contains(w) || seq.reversed().contains(w)
        }
    }
}
private fun hasKeyboardWalk(s: String): Boolean {
    val rows = listOf("`1234567890-=", "qwertyuiop[]\\", "asdfghjkl;'", "zxcvbnm,./")
    val t = s.lowercase()
    return rows.any { row ->
        t.windowed(3, 1).any { w -> row.contains(w) || row.reversed().contains(w) }
    }
}
private fun repeatPenalty(s: String): Double {
    val longestRun = s.fold(Pair<Char?, Int>(null, 0)) { acc, c ->
        val (prev, len) = acc
        if (prev == c) prev to (len + 1) else c to 1
    }.second
    return when {
        longestRun >= 6 -> 1.0
        longestRun >= 4 -> 0.6
        longestRun >= 3 -> 0.3
        else -> 0.0
    }
}