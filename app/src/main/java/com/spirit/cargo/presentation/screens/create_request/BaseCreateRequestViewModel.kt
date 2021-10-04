package com.spirit.cargo.presentation.screens.create_request

import androidx.lifecycle.ViewModel
import com.spirit.cargo.presentation.core.Changes
import io.reactivex.rxjava3.core.Observable


abstract class BaseCreateRequestViewModel : ViewModel() {

    data class State(val linkError: Int? = null)

    abstract val state: Observable<State>


    abstract fun startCreateRequestFlow(title: String, url: String)

    class ErrorChanges(private val urlValidationError: Int) : Changes<State> {
        override fun invoke(subject: State): State {
            return subject.copy(linkError = urlValidationError)
        }
    }
}