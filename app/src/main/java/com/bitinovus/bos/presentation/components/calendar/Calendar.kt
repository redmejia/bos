package com.bitinovus.bos.presentation.components.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentation.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite90
import java.time.LocalDate
import kotlin.text.replaceFirstChar
import kotlin.text.uppercase

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Calendar(
    weekDay: List<String>,
    days: List<String>,
) {
    val today = LocalDate.now()
    val todayDay = today.toString().split("-")[2]

    FlowRow(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 7
    ) {

        repeat(weekDay.size) {
            BoxContainer(
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .background(
                        color = if (todayDay == days[it]) PrimaryWhite90 else Color.Transparent,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
            ) {
                Text(
                    weekDay[it].replaceFirstChar { it.uppercase() },
                    color = PrimaryWhite00
                )
            }
        }
        repeat(weekDay.size) {
            BoxContainer(
                modifier = Modifier
                    .background(
                        color = if (todayDay == days[it]) PrimaryWhite90 else Color.Transparent,
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
            ) {
                Text(
                    days[it],
                    color = PrimaryWhite00,
                    fontWeight = FontWeight.SemiBold
                )
                Indicator(isActive = todayDay == days[it])
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
            .size(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
private fun Indicator(isActive: Boolean) {
    Canvas(modifier = Modifier.size(10.dp)) {
        drawCircle(color = if (isActive) PrimaryRed00 else Color.Transparent)
    }
}