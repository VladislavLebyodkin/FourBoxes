package com.vladosapps.fourboxes.feature.login.domain

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return repository.loginWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}