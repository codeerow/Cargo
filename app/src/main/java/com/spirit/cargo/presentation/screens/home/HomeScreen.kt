package com.spirit.cargo.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ScreenHomeBinding
import com.spirit.cargo.presentation.screens.home.listItem.RequestsAdapter
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import com.spirit.cargo.utils.bind
import com.spirit.cargo.utils.onSwipe
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeScreen : Fragment(R.layout.screen_home) {

    private val viewModel by viewModel<BaseRequestsViewModel> { parametersOf(requireContext()) }

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val requestsAdapter by lazy {
        RequestsAdapter(
            onListenSwitch = { requestId, turnOn ->
                viewModel.startListeningRequestsFlow(turnOn = turnOn, requestId)
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        createNewRequest.setOnClickListener { viewModel.startRequestCreationFlow() }
        requests.apply {
            adapter = requestsAdapter
            onSwipe(ItemTouchHelper.LEFT) { viewHolder, _ ->
                val swipedRequest = requestsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.startDeleteRequestFlow(swipedRequest.id)
            }
        }

        viewModel.bind()
    }

    private fun BaseRequestsViewModel.bind() = with(binding) {
        items.bind(viewLifecycleOwner) {
            requestsAdapter.submitList(it)
            noRequestsHint.isVisible = it.isEmpty()
            bindToggleAllButtonText(it)
        }
    }

    private fun bindToggleAllButtonText(entities: List<RequestItem>) = with(binding) {
        val allIsActive = entities.all { it.isActive }
        toggleAll.setText(if (allIsActive) R.string.turn_off_each_one else R.string.turn_on_each_one)

        toggleAll.setOnClickListener {
            viewModel.startListeningRequestsFlow(
                ids = entities.map { it.id }.toIntArray(),
                turnOn = !allIsActive
            )
        }
    }
}
