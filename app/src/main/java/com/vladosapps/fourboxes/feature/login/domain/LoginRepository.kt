package com.vladosapps.fourboxes.feature.login.domain

import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>
}