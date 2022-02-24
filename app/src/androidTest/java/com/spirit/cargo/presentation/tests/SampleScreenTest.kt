package com.spirit.cargo.presentation.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.spirit.cargo.presentation.HomeScreenApi
import com.spirit.cargo.presentation.MainActivity
import io.github.kakaocup.kakao.screen.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleScreenTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        Screen.onScreen<HomeScreenApi> {
            toggleButton.click()
        }
    }
}
