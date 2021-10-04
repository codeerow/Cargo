package com.spirit.cargo.presentation.screens.home.list_item

import androidx.recyclerview.widget.RecyclerView
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ListItemRequestBinding
import com.spirit.cargo.presentation.screens.home.BaseRequestsViewModel

class RequestViewHolder(
    private val onDeleteClick: (Int) -> Unit,
    private val onListenSwitch: (Int, Boolean) -> Unit,
    private val binding: ListItemRequestBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BaseRequestsViewModel.Model) = with(binding) {
        with(title) {
            text = item.title
        }
        with(toggle) {
            isChecked = item.isActive
            setOnCheckedChangeListener { _, isChecked ->
                onListenSwitch(item.id, isChecked)
            }
        }
        with(ordersCount) {
            text = resources.getString(R.string.orders_count, item.orders)
        }

        root.setOnClickListener {
            onDeleteClick(item.id)
        }
    }
} 