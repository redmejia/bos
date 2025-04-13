package com.bitinovus.bos.presentaion.screens.pos.denominationbuttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.components.buttons.EasyPayButton


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DenominationButtonsSection(denominations: List<String>, maxPerRow: Int = 3) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = maxPerRow
    ) {
        denominations.forEach { denomination ->
            EasyPayButton(
                modifier = Modifier.weight(1f),
                denomination = denomination,
                textStyle = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}
