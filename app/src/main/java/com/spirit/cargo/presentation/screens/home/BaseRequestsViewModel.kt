package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.presentation.core.RxViewModel
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import io.reactivex.rxjava3.core.Observable

abstract class BaseRequestsViewModel : RxViewModel() {

    // output
    abstract val items: Observable<List<RequestItem>>

    // input
    abstract fun startDeleteRequestFlow(id: Int)
    abstract fun startRequestCreationFlow()
    abstract fun startListeningRequestsFlow(turnOn: Boolean, vararg ids: Int)
}
