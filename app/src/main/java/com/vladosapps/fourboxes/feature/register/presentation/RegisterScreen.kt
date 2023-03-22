package com.vladosapps.fourboxes.feature.register.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vladosapps.fourboxes.R

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        EmailField(
            email = viewModel.email,
            emailErrorMessageId = viewModel.emailErrorMessageId,
            validateEmail = { viewModel.validateEmail() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            password = viewModel.password,
            passwordErrorMessageId = viewModel.passwordErrorMessageId,
            validatePassword = { viewModel.validatePassword() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordConfirmField(
            passwordConfirm = viewModel.passwordConfirm,
            passwordConfirmErrorMessageId = viewModel.passwordConfirmErrorMessageId,
            validatePasswordConfirm = { viewModel.validatePasswordConfirm() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(
            isButtonEnabled = viewModel.isSubmitButtonEnabled,
            submit = { viewModel.submit() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: MutableState<String>,
    emailErrorMessageId: MutableState<Int?>,
    validateEmail: () -> Unit
) {
    OutlinedTextField(
        value = email.value,
        onValueChange = {
            email.value = it
            validateEmail()
        },
        label = { Text(text = stringResource(id = R.string.register_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text(text = stringResource(R.string.register_email_hint)) },
        trailingIcon = {
            when {
                email.value.isNotEmpty() -> IconButton(onClick = {
                    email.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = {
            val errorTextId = emailErrorMessageId.value
            errorTextId?.let { Text(text = stringResource(id = errorTextId)) }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: MutableState<String>,
    passwordErrorMessageId: MutableState<Int?>,
    validatePassword: () -> Unit
) {
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password.value,
        onValueChange = {
            password.value = it
            validatePassword()
        },
        label = { Text(text = stringResource(id = R.string.register_password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        placeholder = { Text(text = stringResource(R.string.register_password_hint)) },
        trailingIcon = {
            if (password.value.isNotEmpty()) {
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
        supportingText = {
            val errorTextId = passwordErrorMessageId.value
            errorTextId?.let { Text(text = stringResource(id = errorTextId)) }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordConfirmField(
    passwordConfirm: MutableState<String>,
    passwordConfirmErrorMessageId: MutableState<Int?>,
    validatePasswordConfirm: () -> Unit
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = passwordConfirm.value,
        onValueChange = {
            passwordConfirm.value = it
            validatePasswordConfirm()
        },
        label = { Text(text = stringResource(id = R.string.register_confirm_password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        placeholder = { Text(text = stringResource(R.string.register_confirm_password_hint)) },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        trailingIcon = {
            if (passwordConfirm.value.isNotEmpty()) {
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
        supportingText = {
            val errorTextId = passwordConfirmErrorMessageId.value
            errorTextId?.let { Text(text = stringResource(id = errorTextId)) }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SubmitButton(
    isButtonEnabled: MutableState<Boolean>,
    submit: () -> Unit
) {
    Button(
        onClick = { submit() },
        shape = RoundedCornerShape(size = 16.dp),
        enabled = isButtonEnabled.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register_submit_button_text),
            fontSize = 16.sp
        )
    }
}