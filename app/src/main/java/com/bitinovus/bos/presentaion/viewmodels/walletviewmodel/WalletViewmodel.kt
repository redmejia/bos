package com.bitinovus.bos.presentaion.viewmodels.walletviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.domain.usecases.time.Time
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.TrxType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WalletViewmodel @Inject constructor(
    private val time: Time,
) : ViewModel() {

    private val _walletTransactionState =
        MutableStateFlow<List<WalletTransactionState>>(emptyList())
    val walletTransactionState: StateFlow<List<WalletTransactionState>> =
        _walletTransactionState.asStateFlow()

    val balanceState: StateFlow<Double> =
        walletTransactionState.map { list ->
            list.sumOf { it.amount } / 100.0
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)


    fun todayDate(): String = formatTime(time.now(), "EE, dd, YYYY")

    fun confirmTransaction(amount: Long, trxType: TrxType) {
        try {
            val newTransaction = WalletTransactionState(
                time = time.now(),
                type = trxType,
                amount = amount
            )

            _walletTransactionState.update { currentList ->
                currentList + newTransaction
            }

        } catch (e: Exception) {
            Log.e("ERROR", "confirmTransaction: ${e.message}")
        }

    }

    fun formatTime(trxTime: Long, pattern: String): String =
        time.formater(trxTime, pattern)

}