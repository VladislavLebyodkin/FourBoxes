package com.vladosapps.fourboxes.feature.register.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladosapps.fourboxes.R
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordConfirmValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation
import com.vladosapps.fourboxes.feature.login.presentation.LoginRoute
import com.vladosapps.fourboxes.feature.register.domain.RegisterInteractor
import com.vladosapps.fourboxes.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val interactor: RegisterInteractor,
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableStateFlow(
        RegisterState(
            isLoading = false,
            emailValidation = EmailValidation("", null, false),
            passwordValidation = PasswordValidation("", null, false),
            passwordConfirmValidation = PasswordConfirmValidation("", null, false),
        )
    )

    val state = _state.asStateFlow()

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

    fun onPasswordConfirmChanged(newValue: String) {
        _state.update { state ->
            val passwordConfirmValidation = state.passwordConfirmValidation.copy(passwordConfirm = newValue)
            state.copy(passwordConfirmValidation = passwordConfirmValidation)
        }
        validatePasswordConfirm()
    }

    fun onSubmitClicked() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            interactor.createUserWithEmailAndPassword(
                email = _state.value.emailValidation.email,
                password = _state.value.passwordValidation.password
            )
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        navigator.navigate(LoginRoute(email = it.email!!))
                    }
                }
                .onFailure {
                    // TODO: handle errors | bottom sheet dialog
                }

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun back() {
        navigator.popBackStack()
    }

    fun onLoginClicked() {
        navigator.navigate(LoginRoute())
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

    private fun validatePasswordConfirm() {
        val pass = _state.value.passwordValidation.password
        val passConfirm = _state.value.passwordConfirmValidation.passwordConfirm

        if (!pass.contentEquals(passConfirm)) {
            _state.update {
                val passwordConfirmValidation = it.passwordConfirmValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.register_confirm_passwords_different
                )
                it.copy(passwordConfirmValidation = passwordConfirmValidation)
            }
        } else {
            _state.update {
                val passwordConfirmValidation = it.passwordConfirmValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                it.copy(passwordConfirmValidation = passwordConfirmValidation)
            }
        }
    }
}