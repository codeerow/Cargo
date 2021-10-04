package com.spirit.cargo.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.spirit.cargo.domain.request.CargoRequest
import io.reactivex.rxjava3.core.Observable

abstract class BaseRequestsViewModel : ViewModel() {

    abstract fun startDeleteRequestFlow(id: Int)
    abstract fun startListeningRequestFlow(id: Int, turnOn: Boolean)
    abstract fun startListeningRequestsFlow(ids: List<Int>, turnOn: Boolean)
    abstract fun startRequestCreationFlow()

    abstract val entities: Observable<List<Model>>

    data class Model(
        val id: Int,
        val title: String,
        val url: String,
        val isActive: Boolean,
        val orders: Int
    ) {
        fun toDomain() =
            CargoRequest(id = id, title = title, url = url, isActive = isActive, orders = orders)

        companion object {
            fun fromDomain(model: CargoRequest) = Model(
                id = model.id,
                title = if (model.title.isBlank()) model.url else model.title,
                url = model.url,
                isActive = model.isActive,
                orders = model.orders
            )

            val diffCallback = object : DiffUtil.ItemCallback<Model>() {
                override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}