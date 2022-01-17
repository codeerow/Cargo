package com.spirit.cargo.app.di

import android.content.Context
import androidx.room.Room
import com.spirit.cargo.app.navigation.AACNavigation
import com.spirit.cargo.app.navigation.commands.AACNavigateBackward
import com.spirit.cargo.app.navigation.commands.AACNavigateToCreateRequest
import com.spirit.cargo.app.persistence.AppDatabase
import com.spirit.cargo.data.order.JsoupOrderDataSource
import com.spirit.cargo.data.request.RoomRequestRepository
import com.spirit.cargo.domain.core.navigation.Navigation
import com.spirit.cargo.domain.core.navigation.commands.NavigateBackward
import com.spirit.cargo.domain.core.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.domain.core.validation.ValidateUrl
import com.spirit.cargo.domain.order.OrderDataSource
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.screens.createRequest.BaseCreateRequestViewModel
import com.spirit.cargo.presentation.screens.createRequest.CreateRequestViewModel
import com.spirit.cargo.presentation.screens.createRequest.flows.CreateRequestFlow
import com.spirit.cargo.presentation.screens.home.BaseRequestsViewModel
import com.spirit.cargo.presentation.screens.home.RequestsViewModel
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.services.BaseRefreshOrdersInfoViewModel
import com.spirit.cargo.presentation.services.RefreshOrdersInfoViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.binds
import org.koin.dsl.module

val persistence = module {
    val databaseName = "application_db"
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    single { get<AppDatabase>().requestsDao() }
}

val repositories = module {
    factory<RequestRepository> { RoomRequestRepository(dao = get()) }
    factory<OrderDataSource> { JsoupOrderDataSource() }
}

val validation = module {
    factory { ValidateUrl() }
}

val flows = module {
    factory {
        CreateRequestFlow(
            requestRepository = get(),
            validateUrl = get(),
            navigateBackward = get()
        )
    }
    factory { (context: Context) ->
        SwitchRequestListeningFlow(requestRepository = get(), context = context)
    }
}

val viewModels = module {
    viewModel<BaseRequestsViewModel> { (context: Context) ->
        val requestsToOrders = BehaviorSubject.createDefault(listOf<Pair<Int, Int>>())

        RequestsViewModel(
            observeRequestsToOrders = requestsToOrders,
            requestRepository = get(),
            switchRequestListeningFlow = get { parametersOf(context) },
            navigateToCreateRequest = get()
        )
    }
    viewModel<BaseCreateRequestViewModel> { CreateRequestViewModel(createRequestFlow = get()) }
    factory<BaseRefreshOrdersInfoViewModel> {
        RefreshOrdersInfoViewModel(
            requestRepository = get(),
            orderDataSource = get()
        )
    }
}

val navigation = module {
    single { AACNavigation() }.binds(arrayOf(Navigation::class, AACNavigation::class))

    factory<NavigateBackward> { AACNavigateBackward(navigationHolder = get()) }
    factory<NavigateToCreateRequest> { AACNavigateToCreateRequest(navigationHolder = get()) }
}

val app = persistence + repositories + flows + viewModels + validation + navigation
