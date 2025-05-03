package com.bitinovus.bos.data.local.repository

import com.bitinovus.bos.data.local.dao.TransactionDao
import com.bitinovus.bos.data.local.entities.Transaction
import com.bitinovus.bos.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun addNewTransaction(transaction: Transaction): Long =
        withContext(Dispatchers.IO) {
            transactionDao.insert(transaction)
        }


    override suspend fun getAllTransaction(): Flow<List<Transaction>> = transactionDao.getAll()

    override suspend fun getLastTransaction(): Flow<Transaction?> = transactionDao.getLast()
}