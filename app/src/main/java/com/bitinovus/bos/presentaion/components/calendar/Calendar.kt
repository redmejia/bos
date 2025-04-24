package com.bitinovus.bos.presentaion.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Calendar(
    weekDay: List<String>,
    days: List<String>,
) {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 7
    ) {
        repeat(weekDay.size) {
            BoxContainer {
                Text(weekDay[it])
            }
        }
        repeat(weekDay.size) {
            BoxContainer {
                Text(days[it])
            }
        }
    }
}

@Composable
private fun BoxContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .size(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}
