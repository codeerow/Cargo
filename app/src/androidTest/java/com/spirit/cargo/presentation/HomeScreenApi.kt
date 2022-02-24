package com.spirit.cargo.presentation

import com.spirit.cargo.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class HomeScreenApi : Screen<HomeScreenApi>() {
    val toggleButton = KButton { withId(R.id.toggle_all) }
}
