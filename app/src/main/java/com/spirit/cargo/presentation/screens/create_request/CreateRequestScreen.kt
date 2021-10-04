package com.spirit.cargo.presentation.screens.create_request

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ScreenCreateRequestBinding
import com.spirit.cargo.presentation.screens.create_request.flows.CreateRequestFlow
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class CreateRequestScreen : Fragment(R.layout.screen_create_request) {

    private val binding by viewBinding(ScreenCreateRequestBinding::bind)
    private val createRequestFlow by inject<CreateRequestFlow>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        link.setText("https://della.eu/ru-by/search/a16b144d16e142fl5ol1z1z2z3z4z5z60z7z9y1y2y3h0ilk0m1.html")
        create.setOnClickListener {
            createRequestFlow(title = title.text.toString(), url = link.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { findNavController().navigateUp() }
                .subscribe()
        }
    }
}