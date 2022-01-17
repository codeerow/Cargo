package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.core.ViewModelTest
import com.spirit.cargo.core.test_data.Common
import com.spirit.cargo.domain.core.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import com.spirit.cargo.presentation.screens.home.model.RequestItem.Companion.toRequestItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class RequestsViewModelTest : ViewModelTest() {

    @Test
    fun `ViewModel load initial items finish with success`() {
        // GIVEN
        val initialItems = Common.requests
        val expectedItems = initialItems.map { it.toRequestItem(1) }
        val repo: RequestRepository = mockk {
            every { observe() } returns Observable.just(initialItems)
        }

        // WHEN
        val sut = buildViewModel(requestRepo = repo)
        scheduler.triggerActions()

        // THEN
        sut.items.test().assertValue(expectedItems)
    }

    @Test
    fun `ViewModel load initial items finish with failure`() {
        // GIVEN
        val expectedItems = listOf<RequestItem>()
        val repo: RequestRepository = mockk {
            every { observe() } returns Observable.error(RuntimeException())
        }

        // WHEN
        val sut = buildViewModel(requestRepo = repo)
        scheduler.triggerActions()

        // THEN
        sut.items.test().assertValue(expectedItems)
    }

    @Test
    fun `ViewModel delete method invoke delete flow`() {
        // GIVEN
        val idToDelete = 1
        val repo: RequestRepository = mockk(relaxed = true)
        val sut = buildViewModel(requestRepo = repo)

        // WHEN
        sut.startDeleteRequestFlow(id = idToDelete)
        scheduler.triggerActions()

        // THEN
        verify(exactly = 1) { repo.delete(id = idToDelete) }
    }

    @Test
    fun `ViewModel invoke start creation flow then navigate to next screen`() {
        // GIVEN
        val navigateToCreateRequest: NavigateToCreateRequest = mockk(relaxed = true)
        val sut = buildViewModel(navigateToCreateRequest = navigateToCreateRequest)

        // WHEN
        sut.startRequestCreationFlow()
        scheduler.triggerActions()

        // THEN
        verify(exactly = 1) { navigateToCreateRequest() }
    }

    @Test
    fun `when ViewModel invoke start listening requests flow then listening starts for each request`() {
        // GIVEN
        val requestIds = listOf(1, 2, 3)
        val switchRequestListeningFlow: SwitchRequestListeningFlow = mockk(relaxed = true)
        val sut = buildViewModel(switchRequestListeningFlow = switchRequestListeningFlow)

        // WHEN
        sut.startListeningRequestsFlow(turnOn = true, ids = requestIds.toIntArray())
        scheduler.triggerActions()

        // THEN
        requestIds.forEach {
            verify(exactly = 1) { switchRequestListeningFlow(it, true) }
        }
    }


    private fun buildViewModel(
        requestRepo: RequestRepository = mockk(relaxed = true),
        switchRequestListeningFlow: SwitchRequestListeningFlow = mockk(relaxed = true),
        navigateToCreateRequest: NavigateToCreateRequest = mockk(relaxed = true)
    ) = RequestsViewModel(
        requestRepository = requestRepo,
        switchRequestListeningFlow = switchRequestListeningFlow,
        navigateToCreateRequest = navigateToCreateRequest,
        observeRequestsToOrders = Observable.just(listOf())
    )
}
