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
        loadRequestsFlow().start()
    }

    override fun startDeleteRequestFlow(id: Int) {
        deleteRequestFlow(id = id).start()
    }

    override fun startListeningRequestFlow(id: Int, turnOn: Boolean) {
        switchRequestListeningFlow(id = id, turnOn = turnOn).start()
    }

    override fun startListeningRequestsFlow(ids: List<Int>, turnOn: Boolean) {
        Observable.fromIterable(ids)
            .flatMapCompletable { id -> switchRequestListeningFlow(id = id, turnOn = turnOn) }
            .start()
    }

    override fun startRequestCreationFlow(): Unit = run { navigateToCreateRequest().subscribe() }
}