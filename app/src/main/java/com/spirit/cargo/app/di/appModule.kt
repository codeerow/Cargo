package com.spirit.cargo.app.di

import androidx.room.Room
import com.spirit.cargo.app.persistence.AppDatabase
import com.spirit.cargo.data.order.JsoupReadOrders
import com.spirit.cargo.data.request.commands.*
import com.spirit.cargo.domain.order.ReadOrders
import com.spirit.cargo.domain.request.commands.*
import com.spirit.cargo.presentation.screens.create_request.flows.CreateRequestFlow
import com.spirit.cargo.presentation.screens.home.BaseRequestsViewModel
import com.spirit.cargo.presentation.screens.home.RequestsViewModel
import com.spirit.cargo.presentation.screens.home.flows.DeleteRequestFlow
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.services.BaseRefreshOrdersInfoViewModel
import com.spirit.cargo.presentation.services.RefreshOrdersInfoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
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

val network = module {
    factory { JsoupReadOrders() }
}

val commands = module {
    factory<CreateRequest> { RoomCreateRequest(dao = get()) }
    factory<DeleteRequest> { RoomDeleteRequest(dao = get()) }
    factory<ObserveRequests> { RoomObserveRequests(dao = get()) }
    factory<UpdateRequest> { RoomUpdateRequest(dao = get()) }
    factory<ReadRequest> { RoomReadRequest(dao = get()) }

    factory<ReadOrders> { JsoupReadOrders() }
}

val flows = module {
    factory { CreateRequestFlow(createRequest = get()) }
    factory { DeleteRequestFlow(deleteRequest = get()) }
    factory { SwitchRequestListeningFlow(readRequest = get(), readOrders = get()) }
}

val viewModels = module {
    viewModel<BaseRequestsViewModel> { RequestsViewModel(observeRequests = get()) }
    factory<BaseRefreshOrdersInfoViewModel> {
        RefreshOrdersInfoViewModel(
            readOrders = get(),
            readRequest = get()
        )
    }
}

val app = persistence + network + commands + flows + viewModels