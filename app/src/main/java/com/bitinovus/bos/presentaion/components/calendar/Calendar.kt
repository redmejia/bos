package com.bitinovus.bos.presentaion.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Calendar() {
    val date = LocalDate.now()

    val weekDay = DateTimeFormatter.ofPattern("E") // Mon Tue  Wed
    val day = DateTimeFormatter.ofPattern("dd") // 01 02 03 .. 12 13 15


//    val weekDayList = getDates(date = date, dateFormatter = weekDay)
//    val dayList = getDates(date = date, dateFormatter = day)

    // "06-02-2024"
    // split("-") ["06", "02", "2024"] get the last index [2] = 2024
    val todayDay = date.toString().split("-")[2]
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Calendar()
    }
}