package com.qsoft.auth_presentation.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qsoft.common.util.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(): ViewModel(){


    var state by mutableStateOf(ForgotPasswordState())
        private set

    init {

    }

    fun onEvent(event: ForgotPasswordEvent){
        when(event){
            is ForgotPasswordEvent.OnEmailEnter-> {
                state = state.copy(
                    isMailValid = validateEmail(event.email),
                    email = event.email
                )
            }

            is ForgotPasswordEvent.OnEmailTouchedListener-> {
                state = state.copy(isEmailTouched = true)
            }
        }
    }
}