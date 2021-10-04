package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.presentation.core.Change
import com.spirit.cargo.presentation.core.StateFullViewModel
import com.spirit.cargo.presentation.screens.home.model.RequestItem

abstract class BaseRequestsViewModel : StateFullViewModel<BaseRequestsViewModel.State>(initialState = State()) {

    abstract fun startDeleteRequestFlow(id: Int)
    abstract fun startListeningRequestFlow(id: Int, turnOn: Boolean)
    abstract fun startListeningRequestsFlow(ids: List<Int>, turnOn: Boolean)
    abstract fun startRequestCreationFlow()

    data class State(val items: List<RequestItem> = listOf())

    class LoadRequests(private val items: List<RequestItem>) : Change<State> {
        override fun invoke(subject: State): State {
            return subject.copy(items = items)
        }
    }
}