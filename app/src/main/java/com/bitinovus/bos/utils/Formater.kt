package com.bitinovus.bos.utils

// Price formater
fun currencyFormater(leadingText: String, convertTo: Double): String =
    String.format("${leadingText}%.2f", convertTo)
