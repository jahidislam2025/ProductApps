package com.qsoft.auth_presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qsoft.common.util.UiEvent
import com.qsoft.common.util.validateEmail
import com.qsoft.designsystem.utils.calculatePasswordStrength
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    init {

    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailEnter -> {
                state = state.copy(
                    isMailValid = validateEmail(event.email),
                    email = event.email
                )
            }

            is SignUpEvent.OnPasswordEnter -> {
                val result = calculatePasswordStrength(event.password)

                state = state.copy(
                    strengthResult = result,
                    password = event.password,
                    isPasswordValid =  result.score > 1
                )
            }

            is SignUpEvent.OnEmailTouchedListener -> {
                state = state.copy(isEmailTouched = true)
            }

            is SignUpEvent.OnPasswordTouchedListener -> {
                state = state.copy(isPasswordTouched = true)
            }

            is SignUpEvent.OnNameInput -> {
                state = state.copy(name = event.name)

            }

            is SignUpEvent.OnNameInputTouchedListener -> {
                state = state.copy(isNameTouched = true)
            }

            is SignUpEvent.OnConfirmPasswordTouchedListener -> {
                state = state.copy(isConfirmPasswordTouched = true)
            }

            is SignUpEvent.OnConfirmPasswordEnter -> {
                state = state.copy(
                    confirmPassword = event.password,
                    isConfirmPasswordValid = state.password == event.password
                )
            }

        }
    }
}