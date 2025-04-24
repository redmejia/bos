package com.bitinovus.bos.presentaion.viewmodels.walletviewmodel

data class WalletCalendarState(
    val todayMonthAndYear: String = "",
    val weekDay: List<String> = emptyList(),
    val days: List<String> = emptyList(),
)
