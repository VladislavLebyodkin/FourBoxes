package com.vladosapps.fourboxes.common.model.user

data class PasswordValidation(
    val password: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)