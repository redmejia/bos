package com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.presentaion.viewmodels.appsnack.Snack
import com.bitinovus.bos.presentaion.viewmodels.appsnack.SnackType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewmodel : ViewModel() {

    private val _paymentState = MutableStateFlow<TransactionState>(TransactionState())
    // val paymentState: StateFlow<TransactionState> = _paymentState.asStateFlow()

    private val _paymentSnackBarState = MutableSharedFlow<Snack>()
    val paymentSnackBarState: SharedFlow<Snack> = _paymentSnackBarState.asSharedFlow()

    fun exactAmount(amount: Long) {
        viewModelScope.launch {

            _paymentState.update {
                it.copy(trxAmount = amount, trxType = TrxType.CASH, trxExecuted = true)
            }
            _paymentSnackBarState.emit(
                Snack(
                    message = "0.0 No Action",
                    type = SnackType.SUCCESS,
                )

            )
        }
    }

    fun easyPay(denomination: Long, amount: Long, action: () -> Unit) {
        viewModelScope.launch {
            try {
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
                    // navigate or clear cart list
                    if (_paymentState.value.trxExecuted) {
                        action()
                        _paymentSnackBarState.emit(
                            Snack(
                                message = "${change / 100.00}",
                                type = SnackType.SUCCESS,
                            )
                        )
                    }
                } else {
                    _paymentSnackBarState.emit(
                        Snack(
                            message = "Select a higher amount",
                            type = SnackType.ERROR,
                        )
                    )
                }
            } catch (_: Exception) {
            }
        }

    }
}