package com.bitinovus.bos.presentation.widget.wallet

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class WalletWidgetTransaction(private val transactionRepository: TransactionWidgetRepositoryImpl) {
    suspend fun getBalance(): Double {
        return transactionRepository
            .getAllTransaction()
            .map { list -> list.sumOf { it.trxAmount } / 100.00 }
            .firstOrNull() ?: 0.0
    }
}