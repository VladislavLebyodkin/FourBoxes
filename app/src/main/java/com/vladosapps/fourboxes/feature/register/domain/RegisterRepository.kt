package com.vladosapps.fourboxes.feature.register.domain

import com.google.firebase.auth.FirebaseUser

interface RegisterRepository {
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>
}