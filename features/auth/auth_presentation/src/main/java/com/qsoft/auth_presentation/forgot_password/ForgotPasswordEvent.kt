package com.qsoft.auth_presentation.forgot_password

sealed class ForgotPasswordEvent {
    data class OnEmailEnter(val email: String) : ForgotPasswordEvent()
    object OnEmailTouchedListener : ForgotPasswordEvent()
}