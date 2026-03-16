package com.qsoft.auth_presentation.registration

sealed class SignUpEvent {
    data class OnEmailEnter(val email: String) : SignUpEvent()
    data class OnPasswordEnter(val password: String) : SignUpEvent()
    data object OnEmailTouchedListener : SignUpEvent()
    data object OnPasswordTouchedListener : SignUpEvent()
    data class OnNameInput(val name : String) : SignUpEvent()
    data object OnNameInputTouchedListener : SignUpEvent()
    data object OnConfirmPasswordTouchedListener : SignUpEvent()
    data class OnConfirmPasswordEnter(val password: String) : SignUpEvent()
}