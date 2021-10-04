package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.domain.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.presentation.screens.home.flows.DeleteRequestFlow
import com.spirit.cargo.presentation.screens.home.flows.LoadRequestsFlow
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import io.reactivex.rxjava3.core.Observable


class RequestsViewModel(
    loadRequestsFlow: LoadRequestsFlow,
    private val deleteRequestFlow: DeleteRequestFlow,
    private val switchRequestListeningFlow: SwitchRequestListeningFlow,
    private val navigateToCreateRequest: NavigateToCreateRequest
) : BaseRequestsViewModel() {

    init {
        bindChanges(loadRequestsFlow.changes)
        loadRequestsFlow().startAsync()
    }

    override fun startDeleteRequestFlow(id: Int) {
        deleteRequestFlow(id = id).startAsync()
    }

    override fun startListeningRequestsFlow(turnOn: Boolean, vararg ids: Int) {
        Observable.fromIterable(ids.toList())
            .flatMapCompletable { id -> switchRequestListeningFlow(id = id, turnOn = turnOn) }
            .startAsync()
    }

    override fun startRequestCreationFlow(): Unit = run { navigateToCreateRequest().startAsync() }
}