package com.spirit.cargo.domain.request

data class CargoRequest(
    val id: Int,
    val title: String,
    val url: String,
    val isActive: Boolean
)
