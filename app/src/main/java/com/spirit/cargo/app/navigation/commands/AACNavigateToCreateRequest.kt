package com.spirit.cargo.app.navigation.commands

import com.spirit.cargo.R
import com.spirit.cargo.app.navigation.AACNavigation
import com.spirit.cargo.domain.navigation.commands.NavigateToCreateRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable

class AACNavigateToCreateRequest(private val navigationHolder: AACNavigation) :
    NavigateToCreateRequest {

    override fun invoke(): Completable = with(navigationHolder) {
        return Completable.fromAction { navController.navigate(R.id.action_HomeScreen_to_CreateRequestScreen) }
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}