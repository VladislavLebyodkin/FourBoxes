package com.vladosapps.fourboxes.feature.login.presentation

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladosapps.fourboxes.R
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation
import com.vladosapps.fourboxes.feature.login.domain.LoginInteractor
import com.vladosapps.fourboxes.feature.register.presentation.RegisterRoute
import com.vladosapps.fourboxes.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: LoginInteractor,
    private val navigator: Navigator
) : ViewModel() {

    private val route = LoginRoute(savedStateHandle)

    private val _state = MutableStateFlow(
        LoginState(
            isLoading = false,
            emailValidation = EmailValidation(
                email = if (route.email != LoginRoute.NO_EMAIL) route.email else "",
                errorMessageId = null,
                isValid = route.email != LoginRoute.NO_EMAIL
            ),
            passwordValidation = PasswordValidation("", null, false),
        )
    )

    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onSubmitClicked() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            interactor.loginWithEmailAndPassword(
                email = _state.value.emailValidation.email,
                password = _state.value.passwordValidation.password
            )
                .onSuccess { }
                .onFailure { }

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onEmailChanged(newValue: String) {
        _state.update { state ->
            val emailValidation = state.emailValidation.copy(email = newValue)
            state.copy(emailValidation = emailValidation)
        }
        validateEmail()
    }

    fun onPasswordChanged(newValue: String) {
        _state.update { state ->
            val passwordValidation = state.passwordValidation.copy(password = newValue)
            state.copy(passwordValidation = passwordValidation)
        }
        validatePassword()
    }

    fun back() {
        navigator.popBackStack()
    }

    fun onRegisterClicked() {
        navigator.navigate(RegisterRoute)
    }

    private fun validateEmail() {
        val email = _state.value.emailValidation.email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.update {
                val emailValidation = it.emailValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                it.copy(emailValidation = emailValidation)
            }
        } else {
            _state.update {
                val emailValidation = it.emailValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.common_email_incorrect
                )
                it.copy(emailValidation = emailValidation)
            }
        }
    }

    private fun validatePassword() {
        val pass = _state.value.passwordValidation.password
        if (pass.length < 8) {
            _state.update {
                val passwordValidation = it.passwordValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.common_password_short
                )
                it.copy(passwordValidation = passwordValidation)
            }
        } else {
            _state.update {
                val passwordValidation = it.passwordValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                it.copy(passwordValidation = passwordValidation)
            }
        }
    }
}