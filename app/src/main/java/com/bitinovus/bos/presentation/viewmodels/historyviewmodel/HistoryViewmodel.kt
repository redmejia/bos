package com.bitinovus.bos.presentation.viewmodels.historyviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.local.entities.OrderList
import com.bitinovus.bos.data.local.entities.toOrderList
import com.bitinovus.bos.domain.repository.OrderRepository
import com.bitinovus.bos.domain.repository.TransactionRepository
import com.bitinovus.bos.domain.usecases.time.Time
import com.bitinovus.bos.domain.usecases.writer.ReportWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HistoryViewmodel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val transactionRepository: TransactionRepository,
    private val time: Time,
    private val writer: ReportWriter,
) : ViewModel() {

    private val _orderHistoryState = MutableStateFlow<List<OrderList>>(emptyList())
    val orderHistoryState: StateFlow<List<OrderList>> = _orderHistoryState.asStateFlow()

    private val _historyScreenState = MutableStateFlow(false)
    val historyScreenState: StateFlow<Boolean> = _historyScreenState.asStateFlow()

    private val _reportWriteState = MutableStateFlow(false)
    val reportWriteState: StateFlow<Boolean> = _reportWriteState.asStateFlow()

    init {
        viewModelScope.launch {
            orderRepository
                .getOrderHistory()
                .collect { order ->
                    _orderHistoryState.value = order.toOrderList()
                }
        }
    }

    fun writeReport() {
        viewModelScope.launch {
            try {
                // writing report
                _reportWriteState.value = true
                val reportFileNameUIID = UUID.randomUUID().toString()
                val isReportGenerated = writer.generateGropedReport(
                    fileName = reportFileNameUIID,
                    orderList = _orderHistoryState.value
                )
                if (isReportGenerated) {
                    orderRepository.deleteAll()
                    transactionRepository.deleteAll()
                    // reset to start id from after report is generated
                    orderRepository.resetOrderSequence()
                    transactionRepository.resetTransactionSequence()
                    delay(5000L)
                    // report generated and records deleted
                    _reportWriteState.value = false
                }
            } catch (e: Exception) {
                Log.e("ERROR", "writeReport: $e")
            }
        }
    }

    fun changeHistoryScreenState(state: Boolean) {
        _historyScreenState.value = state
    }

    fun formatTime(trxTime: Long, pattern: String): String =
        time.formater(trxTime, pattern)
}