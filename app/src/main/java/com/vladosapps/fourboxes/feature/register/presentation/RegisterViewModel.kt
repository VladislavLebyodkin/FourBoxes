package com.vladosapps.fourboxes.feature.register.presentation

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladosapps.fourboxes.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    val email = mutableStateOf("")
    private val isEmailValid = MutableStateFlow<Boolean>(false)
    val emailErrorMessageId = mutableStateOf<Int?>(null)

    val password = mutableStateOf("")
    private val isPasswordValid = MutableStateFlow<Boolean>(false)
    val passwordErrorMessageId = mutableStateOf<Int?>(null)

    val passwordConfirm = mutableStateOf("")
    private val isPasswordConfirmValid = MutableStateFlow<Boolean>(false)
    val passwordConfirmErrorMessageId = mutableStateOf<Int?>(null)

    val isSubmitButtonEnabled = mutableStateOf(false)

    init {
        combine(
            isEmailValid,
            isPasswordValid,
            isPasswordConfirmValid
        ) { emailValid, passwordValid, passwordConfigValid ->
            val isEnabled = emailValid && passwordValid && passwordConfigValid
            isSubmitButtonEnabled.value = isEnabled
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)
    }

    fun validateEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            isEmailValid.tryEmit(true)
            emailErrorMessageId.value = null
        } else {
            isEmailValid.tryEmit(false)
            emailErrorMessageId.value = R.string.register_email_incorrect
        }
    }

    fun validatePassword() {
        val pass = password.value
        if (pass.length < 8) {
            isPasswordValid.tryEmit(false)
            passwordErrorMessageId.value = R.string.register_password_short
        } else {
            isPasswordValid.tryEmit(true)
            passwordErrorMessageId.value = null
        }
    }

    fun validatePasswordConfirm() {
        val pass = password.value
        val passConfirm = passwordConfirm.value

        if (!pass.contentEquals(passConfirm)) {
            isPasswordConfirmValid.tryEmit(false)
            passwordConfirmErrorMessageId.value = R.string.register_confirm_passwords_different
        } else {
            isPasswordConfirmValid.tryEmit(true)
            passwordConfirmErrorMessageId.value = null
        }
    }

    fun submit() {

    }
}