package com.spirit.cargo.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.spirit.cargo.domain.model.request.model.CargoRequest
import io.reactivex.rxjava3.core.Observable

abstract class BaseRequestsViewModel : ViewModel() {

    abstract fun startDeleteRequestFlow(id: Int)
    abstract fun startListeningRequestFlow(id: Int, turnOn: Boolean)
    abstract fun startListeningRequestsFlow(ids: List<Int>, turnOn: Boolean)
    abstract fun startRequestCreationFlow()

    abstract val state: Observable<State>

    data class State(val items: List<RequestItem> = listOf())

    data class RequestItem(val id: Int,
                           val title: String,
                           val url: String,
                           val isActive: Boolean,
                           val orders: Int) {
        fun toDomain() =
            CargoRequest(id = id, title = title, url = url, isActive = isActive, orders = orders)

        companion object {
            fun fromDomain(model: CargoRequest) = RequestItem(
                id = model.id,
                title = if (model.title.isBlank()) model.url else model.title,
                url = model.url,
                isActive = model.isActive,
                orders = model.orders
            )

            val diffCallback = object : DiffUtil.ItemCallback<RequestItem>() {
                override fun areItemsTheSame(oldItem: RequestItem, newItem: RequestItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: RequestItem, newItem: RequestItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}