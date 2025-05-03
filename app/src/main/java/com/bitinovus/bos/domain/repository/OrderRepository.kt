package com.bitinovus.bos.domain.repository

import com.bitinovus.bos.data.local.entities.Order

interface OrderRepository {
    suspend fun addNewOrder(order: List<Order>)
}