package com.bitinovus.bos.domain.usecases.time

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Time {
    // Unix current time
    // private val time = System.currentTimeMillis()

    fun now(): Long = System.currentTimeMillis()

    fun formater(time: Long, pattern: String): String {
        val formater = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
        return formater.format(Instant.ofEpochMilli(time))
    }

}