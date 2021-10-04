package com.spirit.cargo.presentation.screens.home.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ListItemRequestBinding
import com.spirit.cargo.presentation.screens.home.model.RequestItem


class RequestsAdapter(
    private val onDeleteClick: (Int) -> Unit,
    private val onListenSwitch: (Int, Boolean) -> Unit
) : ListAdapter<
        RequestItem,
        RequestViewHolder>(RequestItem.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder(
            onDeleteClick = onDeleteClick,
            onListenSwitch = onListenSwitch,
            binding = ListItemRequestBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_request, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}