package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.presentation.core.RxViewModel
import io.reactivex.rxjava3.core.Observable

abstract class BaseCreateRequestViewModel : RxViewModel() {

    abstract val linkError: Observable<Int>
    abstract val isLoading: Observable<Boolean>

    abstract fun startCreateRequestFlow(title: String, url: String)
}
