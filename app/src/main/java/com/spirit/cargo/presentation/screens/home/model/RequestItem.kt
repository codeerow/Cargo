package com.spirit.cargo.presentation.screens.home.model

import androidx.recyclerview.widget.DiffUtil
import com.spirit.cargo.domain.request.CargoRequest

data class RequestItem(
    val id: Int,
    val title: String,
    val url: String,
    val isActive: Boolean,
    val ordersCount: Int
) {
    fun toDomain() =
        CargoRequest(id = id, title = title, url = url, isActive = isActive)

    companion object {
        fun CargoRequest.toRequestItem(ordersCount: Int) = RequestItem(
            id = id,
            title = if (title.isBlank()) url else title,
            url = url,
            isActive = isActive,
            ordersCount = ordersCount
        )

        val diffCallback = object : DiffUtil.ItemCallback<RequestItem>() {
            override fun areItemsTheSame(oldItem: RequestItem, newItem: RequestItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RequestItem,
                newItem: RequestItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
