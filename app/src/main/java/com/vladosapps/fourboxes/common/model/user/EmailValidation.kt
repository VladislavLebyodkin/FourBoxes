package com.vladosapps.fourboxes.common.model.user

data class EmailValidation(
    val email: String,
    val errorMessageId: Int?,
    val isValid: Boolean
)