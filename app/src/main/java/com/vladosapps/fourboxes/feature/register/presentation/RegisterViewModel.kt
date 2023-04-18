package com.vladosapps.fourboxes.feature.register.presentation

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.vladosapps.fourboxes.R
import com.vladosapps.fourboxes.common.model.error.FirebaseError
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation
import com.vladosapps.fourboxes.core.BaseViewModel
import com.vladosapps.fourboxes.feature.login.presentation.LoginRoute
import com.vladosapps.fourboxes.feature.register.domain.RegisterInteractor
import com.vladosapps.fourboxes.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val interactor: RegisterInteractor,
    private val navigator: Navigator,
) : BaseViewModel<RegisterState>() {

//    TODO check state update
    override fun createInitialState(): RegisterState {
//        return RegisterState(
//            isLoading = false,
//            emailValidation = EmailValidation("ff@ff.ff", null, true),
//            passwordValidation = PasswordValidation("ffffffff", null, true),
//            passwordConfirmValidation = PasswordValidation("ffffffff", null, true),
//        )
        return RegisterState(
            isLoading = false,
            emailValidation = EmailValidation("", null, false),
            passwordValidation = PasswordValidation("", null, false),
            passwordConfirmValidation = PasswordValidation("", null, false),
        )
    }

    fun onEmailChanged(newValue: String) {
        updateState { state ->
            val emailValidation = state.emailValidation.copy(email = newValue)
            state.copy(emailValidation = emailValidation)
        }
        validateEmail()
    }

    fun onPasswordChanged(newValue: String) {
        updateState { state ->
            val passwordValidation = state.passwordValidation.copy(password = newValue)
            state.copy(passwordValidation = passwordValidation)
        }
        validatePassword()
    }

    fun onPasswordConfirmChanged(newValue: String) {
        updateState { state ->
            val passwordConfirmValidation = state.passwordConfirmValidation.copy(password = newValue)
            state.copy(passwordConfirmValidation = passwordConfirmValidation)
        }
        validatePasswordConfirm()
    }

    fun onSubmitClicked() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            interactor.createUserWithEmailAndPassword(
                email = currentState.emailValidation.email,
                password = currentState.passwordValidation.password
            )
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        navigator.navigate(LoginRoute(email = it.email!!))
                    }
                }
                .onFailure { error -> setError(error = FirebaseError(error.localizedMessage)) }

            updateState { it.copy(isLoading = false) }
        }
    }

    fun back() {
        navigator.popBackStack()
    }

    fun onLoginClicked() {
        navigator.navigate(LoginRoute())
    }

    private fun validateEmail() {
        val email = currentState.emailValidation.email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            updateState { state ->
                val emailValidation = state.emailValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                state.copy(emailValidation = emailValidation)
            }
        } else {
            updateState { state ->
                val emailValidation = state.emailValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.common_email_incorrect
                )
                state.copy(emailValidation = emailValidation)
            }
        }
    }

    private fun validatePassword() {
        val pass = currentState.passwordValidation.password
        if (pass.length < 8) {
            updateState { state ->
                val passwordValidation = state.passwordValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.common_password_short
                )
                state.copy(passwordValidation = passwordValidation)
            }
        } else {
            updateState { state ->
                val passwordValidation = state.passwordValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                state.copy(passwordValidation = passwordValidation)
            }
        }
    }

    private fun validatePasswordConfirm() {
        val pass = currentState.passwordValidation.password
        val passConfirm = currentState.passwordConfirmValidation.password

        if (!pass.contentEquals(passConfirm)) {
            updateState {state ->
                val passwordConfirmValidation = state.passwordConfirmValidation.copy(
                    isValid = false,
                    errorMessageId = R.string.register_confirm_passwords_different
                )
                state.copy(passwordConfirmValidation = passwordConfirmValidation)
            }
        } else {
            updateState { state ->
                val passwordConfirmValidation = state.passwordConfirmValidation.copy(
                    isValid = true,
                    errorMessageId = null
                )
                state.copy(passwordConfirmValidation = passwordConfirmValidation)
            }
        }
    }
}