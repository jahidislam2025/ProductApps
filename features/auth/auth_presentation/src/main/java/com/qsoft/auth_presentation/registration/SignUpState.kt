package com.qsoft.auth_presentation.registration

import com.qsoft.designsystem.utils.StrengthResult

data class SignUpState(
    val isMailValid: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isPasswordValid: Boolean = false,
    val confirmPassword: String = "",
    val isConfirmPasswordValid: Boolean = false,
    val isEmailTouched: Boolean = false,
    val isPasswordTouched: Boolean = false,
    val isConfirmPasswordTouched: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val name: String = "",
    val isNameTouched: Boolean = false,
    val strengthResult : StrengthResult = StrengthResult.EMPTY
)
