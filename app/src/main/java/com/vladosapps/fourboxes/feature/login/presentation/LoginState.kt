package com.vladosapps.fourboxes.feature.login.presentation

import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation

data class LoginState(
    val isLoading: Boolean,
    val emailValidation: EmailValidation,
    val passwordValidation: PasswordValidation
) {
    val isSubmitButtonEnabled: Boolean
        get() = emailValidation.isValid && passwordValidation.isValid && !isLoading
}