package com.bitinovus.bos.data.local.repository

import com.bitinovus.bos.data.local.dao.OrderDao
import com.bitinovus.bos.data.local.entities.Order
import com.bitinovus.bos.data.local.entities.OrderHistory
import com.bitinovus.bos.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
) : OrderRepository {
    override suspend fun addNewOrder(order: List<Order>) {
        withContext(Dispatchers.IO) {
            orderDao.insert(order = order)
        }
    }

    override suspend fun getOrderHistory(): Flow<List<OrderHistory>> = orderDao.history()
    override suspend fun deleteAll() = withContext(Dispatchers.IO) { orderDao.delete() }
    override suspend fun resetOrderSequence() = withContext(Dispatchers.IO) { orderDao.reset() }
}