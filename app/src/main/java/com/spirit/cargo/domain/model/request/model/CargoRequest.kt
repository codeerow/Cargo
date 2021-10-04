package com.spirit.cargo.domain.model.request.model

data class CargoRequest(
    val id: Int,
    val title: String,
    val url: String,
    val isActive: Boolean,
    val orders: Int
)