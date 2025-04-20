package com.bitinovus.bos.presentaion.viewmodels.walletviewmodel

import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.TrxType

data class WalletTransactionState(
    val time: Long = 0L,
    val type: TrxType = TrxType.CASH,
    val amount: Long = 0L,
)
