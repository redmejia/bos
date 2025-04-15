package com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaymentViewmodel : ViewModel() {

    private val _paymentState = MutableStateFlow<TransactionState>(TransactionState())
    val paymentState: StateFlow<TransactionState> = _paymentState.asStateFlow()


    fun exactAmount(amount: Long) {
        _paymentState.update {
            it.copy(trxAmount = amount, trxType = TrxType.CASH, trxExecuted = true)
        }
        Log.d("TRX", "Transaction Executed: ${_paymentState.value}")
    }

    fun easyPay(denomination: Long, amount: Long) {
        if (denomination >= amount) {
            val change = denomination - amount
            _paymentState.update {
                it.copy(
                    trxAmount = amount,
                    trxType = TrxType.CASH,
                    trxExecuted = true,
                    change = change
                )
            }
            Log.d("TRX", "Transaction Executed with Change: ${change / 100.00} cents")
        } else {
            Log.d("TRX", "easyPay: transaction can not be applied")

        }
    }
}