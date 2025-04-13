package com.bitinovus.bos.presentaion.screens.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentaion.components.buttons.EasyPayButton
import com.bitinovus.bos.presentaion.components.summarysection.SummarySection
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80

// Checkout Screen
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Pos() {
    Column(
        modifier = Modifier.padding(4.dp)
    ){
        Column(
            modifier = Modifier
                .background(color = PrimaryGrayBase80, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .padding(2.dp),
        ) {
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = "Total",
                trailingText = "$ 40.00",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = "Total items",
                trailingText = "4",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
        }
        Spacer(Modifier.height(10.dp))
        val denominationList = listOf("1", "5", "10", "20", "50", "100", "exact".uppercase())
        val row = 3
        FlowRow(
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

        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { }
        )
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