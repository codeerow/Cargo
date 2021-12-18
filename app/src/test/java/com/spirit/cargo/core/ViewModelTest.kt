package com.spirit.cargo.core

import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before

open class ViewModelTest {

    protected val scheduler = TestScheduler()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
    }
}
