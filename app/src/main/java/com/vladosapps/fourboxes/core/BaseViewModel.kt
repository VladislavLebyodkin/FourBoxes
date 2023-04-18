package com.vladosapps.fourboxes.core

import androidx.lifecycle.ViewModel
import com.vladosapps.fourboxes.common.model.error.FirebaseError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : Any> : ViewModel() {

    private val _viewState by lazy { MutableStateFlow(createInitialState()) }
    val viewState = _viewState.asStateFlow()

    private val _errorState = MutableStateFlow<FirebaseError?>(null)
    val errorState = _errorState.asStateFlow()

    protected abstract fun createInitialState(): S

    protected val currentState = viewState.value

    protected fun updateState(update: (S) -> S) {
        _viewState.update(update)
    }

    protected fun setError(error: FirebaseError) {
        _errorState.value = error
    }
}