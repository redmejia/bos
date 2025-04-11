package com.bitinovus.bos.presentaion.screens.pos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.components.buttons.EasyPayButton

// Checkout Screen
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Pos() {
    Column {
        val denominationList = listOf("1", "5", "10", "20", "50", "100", "exact".uppercase())
        val row = 3

        FlowRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = row
        ) {
            repeat(denominationList.size) {
                EasyPayButton(
                    modifier = Modifier
                        .weight(1f),
                    denomination = denominationList[it],
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PosPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Pos()
    }
}