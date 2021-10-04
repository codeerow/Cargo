package com.spirit.cargo.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ScreenHomeBinding
import com.spirit.cargo.presentation.screens.home.BaseRequestsViewModel.Model
import com.spirit.cargo.presentation.screens.home.flows.DeleteRequestFlow
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.screens.home.list_item.RequestsAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreen : Fragment(R.layout.screen_home) {

    private val viewModel by viewModel<BaseRequestsViewModel>()
    private val deleteRequestFlow by inject<DeleteRequestFlow>()
    private val switchRequestListeningFlow by inject<SwitchRequestListeningFlow>()

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val requestsAdapter = RequestsAdapter(
        onDeleteClick = { requestId ->
            deleteRequestFlow(id = requestId)
                .subscribeOn(Schedulers.io())
                .subscribe()
        },

        onListenSwitch = { requestId, turnOn ->
            switchRequestListeningFlow(id = requestId, turnOn = turnOn, context = requireActivity())
                .subscribeOn(Schedulers.io())
                .subscribe()
        })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        requests.adapter = requestsAdapter
        viewModel.bind()

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun BaseRequestsViewModel.bind() {
        entities.observeOn(AndroidSchedulers.mainThread())
            .doOnNext(requestsAdapter::submitList)
            .doOnNext(::bindNoRequestHintVisibility)
            .doOnNext(::bindToggleAllButtonText)
            .subscribe()
    }

    private fun bindNoRequestHintVisibility(entities: List<*>) = with(binding) {
        noRequestsHint.isVisible = entities.isEmpty()
    }

    private fun bindToggleAllButtonText(entities: List<Model>) = with(binding) {
        val allIsActive = entities.all { it.isActive }
        toggleAll.setText(
            if (allIsActive) R.string.turn_off_each_one
            else R.string.turn_on_each_one
        )

        toggleAll.setOnClickListener {
            Observable.fromIterable(entities)
                .flatMapCompletable {
                    switchRequestListeningFlow(
                        id = it.id,
                        turnOn = !allIsActive,
                        context = requireActivity()
                    )
                }.subscribeOn(Schedulers.io()).subscribe()
        }
    }
}