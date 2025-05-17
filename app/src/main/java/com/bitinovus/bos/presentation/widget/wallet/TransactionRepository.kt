package com.bitinovus.bos.presentation.widget.wallet

import android.content.Context
import com.bitinovus.bos.data.local.AppDatabase
import com.bitinovus.bos.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionWidgetRepositoryImpl(private val context: Context) {
    fun getAllTransaction(): Flow<List<Transaction>> {
        val db = AppDatabase.getInstance(context)
        return db.transactionDao().getAll()
    }
}