package com.bitinovus.bos.domain.repository

import com.bitinovus.bos.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

// Db transaction repository
interface TransactionRepository {
    suspend fun addNewTransaction(transaction: Transaction): Long
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getLastTransaction(): Flow<Transaction?>
}