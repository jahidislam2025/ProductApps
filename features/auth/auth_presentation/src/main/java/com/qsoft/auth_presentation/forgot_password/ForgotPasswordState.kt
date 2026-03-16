package com.qsoft.auth_presentation.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val isEmailTouched: Boolean = false,
    val isMailValid: Boolean = true
)
