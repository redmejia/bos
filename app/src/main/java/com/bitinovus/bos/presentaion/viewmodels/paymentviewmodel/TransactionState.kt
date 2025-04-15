package com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel

data class TransactionState(
    val trxAmount: Long = 0L,
    val trxType: TrxType = TrxType.CASH,
    val trxExecuted: Boolean = false,
    val change: Long = 0L
)

enum class TrxType {
    CASH,
    VISA
}