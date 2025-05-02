package com.bitinovus.bos.data.local.repository

import com.bitinovus.bos.data.local.dao.OrderDao
import com.bitinovus.bos.data.local.entities.Order
import com.bitinovus.bos.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
) : OrderRepository {
    override suspend fun addNewOrder(oder: List<Order>) {
        TODO("Not yet implemented")
    }
}