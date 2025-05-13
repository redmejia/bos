package com.bitinovus.bos.domain.repository

import com.bitinovus.bos.data.local.entities.Order
import com.bitinovus.bos.data.local.entities.OrderHistory
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun addNewOrder(order: List<Order>)
    suspend fun getOrderHistory(): Flow<List<OrderHistory>>
    suspend fun deleteAll()
    suspend fun resetOrderSequence()
}