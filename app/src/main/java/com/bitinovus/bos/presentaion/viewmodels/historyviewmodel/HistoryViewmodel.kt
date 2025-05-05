package com.bitinovus.bos.presentaion.viewmodels.historyviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.local.entities.OrderHistoryList
import com.bitinovus.bos.data.local.entities.toOrderHistoryList
import com.bitinovus.bos.domain.repository.OrderRepository
import com.bitinovus.bos.domain.usecases.time.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewmodel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val time: Time,
) : ViewModel() {

    private val _orderHistoryState = MutableStateFlow<List<OrderHistoryList>>(emptyList())
    val orderHistoryState: StateFlow<List<OrderHistoryList>> = _orderHistoryState.asStateFlow()

    init {
        viewModelScope.launch {
            _orderHistoryState.value = orderRepository
                .getOrderHistory()
                .toOrderHistoryList()
        }
    }

    fun formatTime(trxTime: Long, pattern: String): String =
        time.formater(trxTime, pattern)
}