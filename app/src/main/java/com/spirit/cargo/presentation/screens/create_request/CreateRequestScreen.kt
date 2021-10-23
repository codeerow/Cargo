package com.spirit.cargo.presentation.screens.create_request

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ScreenCreateRequestBinding
import com.spirit.cargo.utils.subscribe
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateRequestScreen : Fragment(R.layout.screen_create_request) {

    private val viewModel by viewModel<BaseCreateRequestViewModel>()

    private val binding by viewBinding(ScreenCreateRequestBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        link.setText("https://della.eu/ru-by/search/a16b144d16e142fl5ol1z1z2z3z4z5z60z7z9y1y2y3h0ilk0m1.html")

        create.setOnClickListener {
            viewModel.startCreateRequestFlow(
                title = title.text.toString(),
                url = link.text.toString()
            )
        }
        viewModel.bind()
    }

    private fun BaseCreateRequestViewModel.bind() {
        state.observeOn(AndroidSchedulers.mainThread())
            .doOnNext { it.linkError?.let(::bindUrlError) }
            .subscribe(viewLifecycleOwner)
    }

    private fun bindUrlError(errorRes: Int) {
        Toast.makeText(requireContext(), errorRes, Toast.LENGTH_LONG).show()
    }
}