package com.vladosapps.fourboxes.common.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.vladosapps.fourboxes.R
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    emailValidation: EmailValidation,
    onEmailChanged: (newValue: String) -> Unit,
) {
    val email = emailValidation.email
    val errorMessageId = emailValidation.errorMessageId
    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChanged(it) },
        label = { Text(text = stringResource(id = R.string.common_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        placeholder = { Text(text = stringResource(R.string.common_email_hint)) },
        isError = errorMessageId != null,
        trailingIcon = {
            when {
                email.isNotEmpty() -> IconButton(onClick = {
                    onEmailChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = { errorMessageId?.let { Text(text = stringResource(id = errorMessageId)) }},
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    passwordValidation: PasswordValidation,
    onPasswordChanged: (newValue: String) -> Unit,
) {
    val password = passwordValidation.password
    val errorMessageId = passwordValidation.errorMessageId
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChanged(it) },
        label = { Text(text = stringResource(id = R.string.common_password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        placeholder = { Text(text = stringResource(R.string.common_password_hint)) },
        isError = errorMessageId != null,
        trailingIcon = {
            if (password.isNotEmpty()) {
                val icon = if (passwordVisible.value) {
                    painterResource(id = R.drawable.ic_visibility_on)
                } else {
                    painterResource(id = R.drawable.ic_visibility_off)
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(painter = icon, null)
                }
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = { errorMessageId?.let { Text(text = stringResource(id = errorMessageId)) }},
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
