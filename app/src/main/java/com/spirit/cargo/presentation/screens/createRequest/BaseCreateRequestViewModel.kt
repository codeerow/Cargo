package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.presentation.core.Change
import com.spirit.cargo.presentation.core.StateFulViewModel

abstract class BaseCreateRequestViewModel :
    StateFulViewModel<BaseCreateRequestViewModel.State>(initialState = State()) {

    data class State(
        val linkError: Int? = null,
        val isLoading: Boolean = false
    )

    abstract fun startCreateRequestFlow(title: String, url: String)

    class ErrorChange(private val urlValidationError: Int) : Change<State> {
        override fun invoke(subject: State): State {
            return subject.copy(linkError = urlValidationError, isLoading = false)
        }
    }

    class LoadingChange : Change<State> {
        override fun invoke(subject: State): State {
            return subject.copy(isLoading = true, linkError = null)
        }
    }
}
