package com.vladosapps.fourboxes.feature.register.domain

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RegisterInteractor @Inject constructor(
    private val registerRepo: RegisterRepository
) {

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return registerRepo.createUserWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}