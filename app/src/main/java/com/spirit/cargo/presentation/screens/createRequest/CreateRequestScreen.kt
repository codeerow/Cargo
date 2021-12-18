package com.spirit.cargo.presentation.screens.createRequest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ScreenCreateRequestBinding
import com.spirit.cargo.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateRequestScreen : Fragment(R.layout.screen_create_request) {

    private val viewModel by viewModel<BaseCreateRequestViewModel>()
    private val binding by viewBinding(ScreenCreateRequestBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        create.setOnClickListener {
            viewModel.startCreateRequestFlow(
                title = title.text.toString(),
                url = link.text.toString()
            )
        }
        viewModel.bind()
    }

    private fun BaseCreateRequestViewModel.bind() {
        linkError.bind(viewLifecycleOwner, onNext = ::bindUrlError)
    }

    private fun bindUrlError(errorRes: Int) {
        Toast.makeText(requireContext(), errorRes, Toast.LENGTH_LONG).show()
    }
}
