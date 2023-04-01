package com.vladosapps.fourboxes.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladosapps.fourboxes.common.model.user.EmailValidation
import com.vladosapps.fourboxes.common.model.user.PasswordValidation
import com.vladosapps.fourboxes.feature.login.domain.LoginInteractor
import com.vladosapps.fourboxes.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: LoginInteractor,
    private val navigator: Navigator
): ViewModel() {

    private val route = LoginRoute(savedStateHandle)

    private val _state = MutableStateFlow(
        LoginState(
            isLoading = false,
            emailValidation = EmailValidation(route.email ?: "", null, route.email == null),
            passwordValidation = PasswordValidation("", null, false),
        )
    )

    val state = _state.asStateFlow()

    fun onLoginClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                interactor.loginWithEmailAndPassword(
                    email = _state.value.emailValidation.email,
                    password = _state.value.passwordValidation.password
                )
                    .onSuccess {  }
                    .onFailure {  }
            }
        }
    }
}