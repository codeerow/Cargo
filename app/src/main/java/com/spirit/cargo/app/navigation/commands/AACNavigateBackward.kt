package com.spirit.cargo.app.navigation.commands

import com.spirit.cargo.app.navigation.AACNavigation
import com.spirit.cargo.domain.core.navigation.commands.NavigateBackward
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable

class AACNavigateBackward(private val navigationHolder: AACNavigation) : NavigateBackward {

    override operator fun invoke(): Completable = with(navigationHolder) {
        return Completable.fromAction { navController.navigateUp() }
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}
