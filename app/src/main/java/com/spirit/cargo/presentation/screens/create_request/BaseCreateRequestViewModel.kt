package com.spirit.cargo.presentation.screens.create_request

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable


abstract class BaseCreateRequestViewModel : ViewModel() {

    data class Model(val linkError: Int?)

    abstract val model: Observable<Model>


    abstract fun startCreateRequestFlow(title: String, url: String)
}