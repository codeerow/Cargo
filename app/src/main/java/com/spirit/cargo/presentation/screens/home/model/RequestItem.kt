package com.spirit.cargo.presentation.screens.home.model

import androidx.recyclerview.widget.DiffUtil
import com.spirit.cargo.domain.request.CargoRequest

data class RequestItem(
    val id: Int,
    val url: String,
    val title: String,
    val ordersCount: Int,
    val isActive: Boolean,
) {
    fun toDomain() = CargoRequest(
        id = id,
        title = title,
        url = url,
    )

    companion object {
        fun CargoRequest.toRequestItem(ordersCount: Int, isActive: Boolean) = RequestItem(
            id = id,
            title = title.ifBlank { url },
            url = url,
            ordersCount = ordersCount,
            isActive = isActive,
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
