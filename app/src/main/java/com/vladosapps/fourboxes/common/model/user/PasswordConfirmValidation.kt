package com.vladosapps.fourboxes.common.model.user

data class PasswordConfirmValidation(
    val passwordConfirm: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)