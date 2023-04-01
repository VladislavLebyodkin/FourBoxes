package com.vladosapps.fourboxes.feature.register.presentation

import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordConfirmValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation

data class RegisterState(
    val isLoading: Boolean,
    val emailValidation: EmailValidation,
    val passwordValidation: PasswordValidation,
    val passwordConfirmValidation: PasswordConfirmValidation,
) {
    val submitButtonEnabled: Boolean
        get() = emailValidation.isValid &&
                passwordValidation.isValid &&
                passwordConfirmValidation.isValid &&
                !isLoading
}

