package com.qsoft.auth_presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qsoft.common.util.USER_MAIL
import com.qsoft.common.util.USER_PASSWORD
import com.qsoft.common.util.UiEvent
import com.qsoft.common.util.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSignIn -> {
                viewModelScope.launch {
                    state = state.copy(
                        isEmailTouched = true,
                        isPasswordTouched = true,
                        isMailValid = validateEmail(state.email),
                        isPasswordValid = state.password.isNotEmpty()
                    )
                    if (state.isMailValid && state.isPasswordValid) {
                        if (state.email == USER_MAIL && state.password == USER_PASSWORD) {
                            _uiEvent.emit(UiEvent.Success)
                        } else {
                            if (state.email != USER_MAIL) {
                                state = state.copy(isMailValid = false)
                            }

                            if (state.password != USER_PASSWORD) {
                                state = state.copy(isPasswordValid = false)
                            }
                        }
                    }
                }
            }

            is LoginEvent.OnEmailEnter -> {
                state = state.copy(
                    isMailValid = validateEmail(event.email),
                    email = event.email
                )
            }

            is LoginEvent.OnPasswordEnter -> {
                state = state.copy(
                    password = event.password,
                    isPasswordValid = event.password.length >= 6
                )
            }

            is LoginEvent.OnEmailTouchedListener -> {
                state = state.copy(isEmailTouched = true)
            }

            is LoginEvent.OnPasswordTouchedListener -> {
                state = state.copy(isPasswordTouched = true)
            }

            is LoginEvent.OnRememberMeChecked -> {
                state = state.copy(isRememberMeChecked = event.state)
            }

        }
    }
}