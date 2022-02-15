package com.spirit.cargo.app.core.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.spirit.cargo.R
import com.spirit.cargo.domain.core.navigation.Navigation
import com.spirit.cargo.utils.doNothing

class AACNavigation : Navigation<AppCompatActivity> {
    lateinit var navController: NavController

    override fun attach(subject: AppCompatActivity) {
        subject.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                navController = androidx.navigation.Navigation.findNavController(
                    subject,
                    R.id.nav_host_fragment_content_main
                )
            }
        })
    }

    override fun detach(subject: AppCompatActivity) = doNothing()
}
