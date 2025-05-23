package com.bitinovus.bos.presentation.viewmodels.walletviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.local.entities.Transaction
import com.bitinovus.bos.domain.repository.TransactionRepository
import com.bitinovus.bos.domain.usecases.time.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WalletViewmodel @Inject constructor(
    private val time: Time,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val _walletTransactionState =
        MutableStateFlow<List<Transaction>>(emptyList())
    val walletTransactionState: StateFlow<List<Transaction>> =
        _walletTransactionState.asStateFlow()

    val balanceState: StateFlow<Double> =
        walletTransactionState.map { list ->
            list.sumOf { it.total } / 100.00
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)


    private val _walletCalendarState = MutableStateFlow<WalletCalendarState>(WalletCalendarState())
    val walletCalendarState: StateFlow<WalletCalendarState> = _walletCalendarState.asStateFlow()

    init {
        refreshBalance()
        walletCalendar()
    }

    fun refreshBalance(){
        viewModelScope.launch {
            transactionRepository
                .getAllTransaction()
                .collect { transactions ->
                    _walletTransactionState.value = transactions
                }
        }
    }

    private fun walletCalendar(timeDateFormater: DateTimeFormatter): List<String> {
        val startDay = LocalDate.now().with(DayOfWeek.MONDAY)
        val dateList = mutableListOf<String>()

        for (d in 0..6) {
            val day = startDay.plusDays(d.toLong())
            dateList.add(day.format(timeDateFormater))
        }
        return dateList
    }

    fun walletCalendar() {
        val weekDayList = walletCalendar(time.dateTimeFormater("E"))
        val dayList = walletCalendar(time.dateTimeFormater("dd"))

        _walletCalendarState.value = WalletCalendarState(
            todayMonthAndYear = formatTime(
                time.now(),
                "MMM YYYY"
            ).replaceFirstChar { it.uppercase() },
            weekDay = weekDayList,
            days = dayList
        )
    }

    fun formatTime(trxTime: Long, pattern: String): String =
        time.formater(trxTime, pattern)

}