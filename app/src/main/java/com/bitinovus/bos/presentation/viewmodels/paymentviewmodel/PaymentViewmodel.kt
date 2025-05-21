package com.bitinovus.bos.presentation.viewmodels.paymentviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.local.entities.Transaction
import com.bitinovus.bos.domain.model.Product
import com.bitinovus.bos.domain.model.toOrderListWithId
import com.bitinovus.bos.domain.repository.OrderRepository
import com.bitinovus.bos.domain.repository.TransactionRepository
import com.bitinovus.bos.domain.usecases.time.Time
import com.bitinovus.bos.presentation.viewmodels.appsnack.Snack
import com.bitinovus.bos.presentation.viewmodels.appsnack.SnackMessageType
import com.bitinovus.bos.presentation.viewmodels.appsnack.SnackStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewmodel @Inject constructor(
    private val time: Time,
    private val transactionRepository: TransactionRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {

    private val _paymentState = MutableStateFlow<Transaction>(Transaction())
    val paymentState: StateFlow<Transaction> = _paymentState.asStateFlow()

    private val _paymentSnackBarState = MutableSharedFlow<Snack>()
    val paymentSnackBarState: SharedFlow<Snack> = _paymentSnackBarState.asSharedFlow()

    init {
        viewModelScope.launch {
            transactionRepository
                .getLastTransaction()
                .collect { transition ->
                    if (transition != null) {
                        _paymentState.value = transition
                    }
                    if (transition == null) {
                        _paymentState.value = Transaction()
                    }
                }
        }
    }

    fun exactAmount(order: List<Product>, grandTotal: Long) {
        viewModelScope.launch {
            try {

                val newTransaction = Transaction(
                    time = time.now(),
                    total = grandTotal,
                    trxAmount = grandTotal, // exact as total
                    type = TransactionType.CASH.name,
                    trxExecuted = true,
                    change = 0 // exact no action need
                )
                val id = transactionRepository.addNewTransaction(transaction = newTransaction)
                orderRepository.addNewOrder(order = order.toOrderListWithId(orderID = id))

                _paymentSnackBarState.emit(
                    Snack(
                        messageType = SnackMessageType.TRX_NO_ACT,
                        type = SnackStateType.SUCCESS,
                    )

                )
            } catch (e: Exception) {
                Log.e("ERROR", "exactAmount: ", e)
            }
        }
    }

    fun easyPay(
        order: List<Product>,
        denominationSelected: Long,
        grandTotal: Long,
        action: () -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                if (denominationSelected >= grandTotal) {
                    val change = denominationSelected - grandTotal
                    val newTransaction = Transaction(
                        time = time.now(),
                        total = grandTotal,
                        trxAmount = denominationSelected,// total
                        type = TransactionType.CASH.name,
                        trxExecuted = true,
                        change = change
                    )
                    val id = transactionRepository.addNewTransaction(transaction = newTransaction)
                    orderRepository.addNewOrder(order = order.toOrderListWithId(orderID = id))
                    action()  // clear list action
                    _paymentSnackBarState.emit(
                        Snack(
                            messageType = SnackMessageType.TRX_SUCCESS,// show trx_successful string resource
                            type = SnackStateType.SUCCESS,
                        )
                    )
                } else {
                    _paymentSnackBarState.emit(
                        Snack(
                            messageType = SnackMessageType.ERROR_AMT, // show error_amount string resource
                            type = SnackStateType.ERROR,
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("ERROR", "easyPay", e)
            }
        }
    }

    fun chargeAmount(
        order: List<Product>,
        amountEntered: Long,
        total: Long,
        action: () -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                if (amountEntered >= total) {
                    val change = amountEntered - total

                    val newTransaction = Transaction(
                        time = time.now(),
                        total = total,
                        trxAmount = amountEntered, // amount
                        type = TransactionType.CASH.name,
                        trxExecuted = true,
                        change = change
                    )

                    val id = transactionRepository.addNewTransaction(transaction = newTransaction)
                    orderRepository.addNewOrder(order = order.toOrderListWithId(orderID = id))
                    action() // clear list action
                    _paymentSnackBarState.emit(
                        Snack(
                            messageType = SnackMessageType.TRX_SUCCESS,// show trx_successful string resource
                            type = SnackStateType.SUCCESS,
                        )
                    )

                } else {
                    _paymentSnackBarState.emit(
                        Snack(
                            messageType = SnackMessageType.ERROR_ENTRY_AMT, // show error_amount string resource
                            type = SnackStateType.ERROR,
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("ERROR", "chargeAmount: ", e)
            }

        }
    }
}