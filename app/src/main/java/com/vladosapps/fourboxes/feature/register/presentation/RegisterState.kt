package com.vladosapps.fourboxes.feature.register.presentation

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

data class EmailValidation(
    val email: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)

data class PasswordValidation(
    val password: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)

data class PasswordConfirmValidation(
    val passwordConfirm: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)
