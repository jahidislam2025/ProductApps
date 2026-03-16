package com.qsoft.auth_presentation.login

sealed class LoginEvent {
    data object OnSignIn: LoginEvent()
    data class OnEmailEnter(val email: String) : LoginEvent()
    data class OnPasswordEnter(val password: String) : LoginEvent()
    data object OnEmailTouchedListener : LoginEvent()
    data object OnPasswordTouchedListener : LoginEvent()
    data class OnRememberMeChecked(val state: Boolean) : LoginEvent()
}