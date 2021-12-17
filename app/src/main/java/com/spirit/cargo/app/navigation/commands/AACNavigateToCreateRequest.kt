package com.spirit.cargo.app.navigation.commands

import com.spirit.cargo.R
import com.spirit.cargo.app.navigation.AACNavigation
import com.spirit.cargo.domain.core.navigation.commands.NavigateToCreateRequest

class AACNavigateToCreateRequest(private val navigationHolder: AACNavigation) :
    NavigateToCreateRequest {

    override operator fun invoke() = with(navigationHolder) {
        navController.navigate(R.id.action_HomeScreen_to_CreateRequestScreen)
    }
}
