package com.spirit.cargo.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule

open class ViewModelTest {

    protected val scheduler = TestScheduler()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
    }
}
