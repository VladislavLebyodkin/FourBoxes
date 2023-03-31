package com.vladosapps.fourboxes.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        addOnCompleteListener {
            val exception = it.exception
            if (exception != null) {
                continuation.resumeWithException(exception)
            } else {
                continuation.resume(it.result, null)
            }
        }
    }
}