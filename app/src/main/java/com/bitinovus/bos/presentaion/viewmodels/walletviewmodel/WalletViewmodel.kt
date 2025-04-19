package com.bitinovus.bos.presentaion.viewmodels.walletviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bitinovus.bos.domain.usecases.time.Time
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.TrxType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WalletViewmodel : ViewModel() {

    private val _walletTransactionState =
        MutableStateFlow<List<WalletTransactionState>>(emptyList())
    val walletTransactionState: StateFlow<List<WalletTransactionState>> =
        _walletTransactionState.asStateFlow()

    private val _balanceState = MutableStateFlow<Double>(value = 0.0)
    val balanceState: StateFlow<Double> = _balanceState.asStateFlow()

    val time = Time()


    private fun updateBalance() {
        _balanceState.value = _walletTransactionState
            .value
            .sumOf { it.amount } / 100.00
    }

    fun confirmTransaction(amount: Long, trxType: TrxType) {
        try {
            val newTransaction = WalletTransactionState(
                time = time.now(),
                type = trxType,
                amount = amount
            )
            _walletTransactionState.value + newTransaction
            updateBalance()

        } catch (e: Exception) {
            Log.e("ERROR", "confirmTransaction: ${e.message}")
        }


    }

}