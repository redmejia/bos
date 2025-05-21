package com.bitinovus.bos.presentation.viewmodels.walletviewmodel

data class WalletCalendarState(
    val todayMonthAndYear: String = "",
    val weekDay: List<String> = emptyList(),
    val days: List<String> = emptyList(),
)
