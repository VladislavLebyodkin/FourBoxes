package com.vladosapps.fourboxes.common.model.error

import kotlin.random.Random

// ПРодумать общую ошибку,
// Либо просто показать сообщение
// Либо была возможность повторить операцию (отвалившийся интернет, например)
class FirebaseError(message: String?) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return Random.nextInt()
    }
}