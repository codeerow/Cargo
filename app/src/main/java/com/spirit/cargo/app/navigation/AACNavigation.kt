package com.spirit.cargo.app.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.spirit.cargo.R
import com.spirit.cargo.domain.navigation.Navigation
import com.spirit.cargo.utils.doNothing


class AACNavigation : Navigation<AppCompatActivity> {
    lateinit var navController: NavController

    override fun attach(subject: AppCompatActivity) {
        subject.lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                navController = findNavController(subject, R.id.nav_host_fragment_content_main)
            }
        })
    }

    override fun detach(subject: AppCompatActivity) = doNothing()
}