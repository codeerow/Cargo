package com.spirit.cargo.viewmodels

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.spirit.cargo.core.ViewModelTest
import com.spirit.cargo.core.test_data.Common
import com.spirit.cargo.domain.core.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.screens.home.RequestsViewModel
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class RequestsViewModelTest : ViewModelTest() {

    @Test
    fun `ViewModel load initial items finish with success`() {
        // GIVEN
        val initialItems = Common.requests
        val expectedItems = initialItems.map(RequestItem::fromDomain)
        val repo: RequestRepository = mock {
            on { observe() }.thenReturn(Observable.just(initialItems))
        }

        val sut = buildViewModel(requestRepo = repo)
        scheduler.triggerActions()

        // WHEN
        sut.items.test()
            // THEN
            .assertValues(expectedItems)
    }

    @Test
    fun `ViewModel load initial items finish with failure`() {
        // GIVEN
        val expectedItems = listOf<RequestItem>()
        val repo: RequestRepository = mock {
            on { observe() }.thenReturn(Observable.error(RuntimeException()))
        }
        val sut = buildViewModel(requestRepo = repo)
        scheduler.triggerActions()

        // WHEN
        sut.items.test()
            // THEN
            .assertValue(expectedItems)
    }

    @Test
    fun `ViewModel delete method invoke delete flow`() {
        // GIVEN
        val idToDelete = 1
        val repo: RequestRepository = mock {
            on { observe() }.thenReturn(Observable.just(listOf()))
            on { delete(idToDelete) }.then { Completable.complete() }
        }
        val sut = buildViewModel(requestRepo = repo)

        // WHEN
        sut.startDeleteRequestFlow(id = idToDelete)
        scheduler.triggerActions()

        // THEN
        verify(repo, times(1)).delete(id = idToDelete)
    }

    @Test
    fun `ViewModel invoke start creation flow then navigate to next screen`() {
        // GIVEN
        val repo: RequestRepository = mock {
            on { observe() }.thenReturn(Observable.just(listOf()))
        }
        val navigateToCreateRequest: NavigateToCreateRequest = mock()
        val sut = buildViewModel(
            requestRepo = repo,
            navigateToCreateRequest = navigateToCreateRequest
        )

        // WHEN
        sut.startRequestCreationFlow()
        scheduler.triggerActions()

        // THEN
        verify(navigateToCreateRequest, times(1)).invoke()
    }

    @Test
    fun `when ViewModel invoke start listening requests flow then listening starts for each request`() {
        // GIVEN
        val requestIds = listOf(1, 2, 3)
        val repo: RequestRepository = mock {
            on { observe() }.thenReturn(Observable.just(listOf()))
        }
        val switchRequestListeningFlow: SwitchRequestListeningFlow = mock {
            on { invoke(any(), any()) }.thenReturn(Completable.complete())
        }
        val sut = buildViewModel(
            requestRepo = repo,
            switchRequestListeningFlow = switchRequestListeningFlow
        )

        // WHEN
        sut.startListeningRequestsFlow(turnOn = true, ids = requestIds.toIntArray())
        scheduler.triggerActions()

        // THEN
        requestIds.forEach {
            verify(switchRequestListeningFlow, times(1)).invoke(it, true)
        }
    }


    private fun buildViewModel(
        requestRepo: RequestRepository = mock(),
        switchRequestListeningFlow: SwitchRequestListeningFlow = mock(),
        navigateToCreateRequest: NavigateToCreateRequest = mock()
    ) = RequestsViewModel(
        requestRepository = requestRepo,
        switchRequestListeningFlow = switchRequestListeningFlow,
        navigateToCreateRequest = navigateToCreateRequest
    )
}
