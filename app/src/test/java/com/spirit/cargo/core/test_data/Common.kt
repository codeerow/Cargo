package com.spirit.cargo.core.test_data

import com.spirit.cargo.domain.request.CargoRequest

object Common {
    val requests = listOf(
        CargoRequest(
            id = 1,
            title = "1",
            url = "1",
            isActive = true,
            orders = 1
        ),
        CargoRequest(
            id = 2,
            title = "2",
            url = "2",
            isActive = false,
            orders = 2
        )
    )
}
