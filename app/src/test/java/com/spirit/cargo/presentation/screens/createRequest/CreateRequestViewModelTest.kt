package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.core.ViewModelTest
import com.spirit.cargo.presentation.screens.createRequest.flows.CreateRequestFlow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class CreateRequestViewModelTest : ViewModelTest() {

    @Test
    fun testGetLinkError() {
        // GIVEN
        val expectedErrorRes = 1
        val flow: CreateRequestFlow = mockk(relaxed = true)
        every { flow.errors } returns Observable.just(expectedErrorRes)

        // WHEN
        val sut = buildViewModel(createRequestFlow = flow)
        val testObserver = sut.linkError.test()
        scheduler.triggerActions()

        // THEN
        testObserver.assertValue(expectedErrorRes)
    }

    @Test
    fun testIsLoading() {
        // GIVEN
        val initialLoadingState = false
        val expectedLoadingState = true
        val flow: CreateRequestFlow = mockk(relaxed = true)
        every { flow.isLoading } returns Observable.just(expectedLoadingState)

        // WHEN
        val sut = buildViewModel(createRequestFlow = flow)
        val testObserver = sut.isLoading.test()
        scheduler.triggerActions()

        // THEN
        testObserver.assertValues(initialLoadingState, expectedLoadingState)
    }

    @Test
    fun testStartCreateRequestFlow() {
        // GIVEN
        val flow: CreateRequestFlow = mockk(relaxed = true)
        val (title, url) = "title" to "url"

        // WHEN
        val sut = buildViewModel(createRequestFlow = flow)
        sut.startCreateRequestFlow(title = title, url = url)
        scheduler.triggerActions()

        // THEN
        verify(exactly = 1) { flow(title = title, url = url) }
    }


    private fun buildViewModel(createRequestFlow: CreateRequestFlow = mockk()) =
        CreateRequestViewModel(createRequestFlow = createRequestFlow)
}
