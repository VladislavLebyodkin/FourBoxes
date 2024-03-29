package com.vladosapps.fourboxes.feature.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vladosapps.fourboxes.R
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation
import com.vladosapps.fourboxes.common.presentation.FullScreenLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.login_title), color = colorResource(id = R.color.white)) },
            modifier = Modifier.fillMaxWidth(),

            navigationIcon = {
                IconButton(onClick = viewModel::back) {
                    Icon(Icons.Filled.ArrowBack, null, tint = colorResource(id = R.color.white))
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = colorResource(id = R.color.purple_500))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = !state.isLoading, onClick = {})
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                EmailField(
                    emailValidation = state.emailValidation,
                    onEmailChanged = { viewModel.onEmailChanged(it) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    passwordValidation = state.passwordValidation,
                    onPasswordChanged = { viewModel.onPasswordChanged(it) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                SubmitButton(
                    isButtonEnabled = state.isSubmitButtonEnabled,
                    onSubmitClicked = { viewModel.onSubmitClicked() }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = stringResource(id = R.string.login_have_no_account))

                Spacer(modifier = Modifier.height(24.dp))

                RegisterButton(viewModel::onRegisterClicked)
            }
        }
    }

    FullScreenLoading(isLoading = state.isLoading)
}

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

@Composable
fun SubmitButton(
    isButtonEnabled: Boolean,
    onSubmitClicked: () -> Unit
) {
    Button(
        onClick = { onSubmitClicked() },
        shape = RoundedCornerShape(size = 16.dp),
        enabled = isButtonEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_submit_button_text),
            fontSize = 16.sp
        )
    }
}

@Composable
fun RegisterButton(onClicked: () -> Unit) {
    Button(
        onClick = { onClicked() },
        shape = RoundedCornerShape(size = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_register_button_text),
            fontSize = 16.sp
        )
    }
}