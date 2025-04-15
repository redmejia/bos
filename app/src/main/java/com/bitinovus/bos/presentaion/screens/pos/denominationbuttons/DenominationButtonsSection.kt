package com.bitinovus.bos.presentaion.screens.pos.denominationbuttons

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.components.buttons.EasyPayButton
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DenominationButtonsSection(
    amount: Long,
    paymentViewmodel: PaymentViewmodel,
    denominations: List<String>,
    maxPerRow: Int = 3,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = maxPerRow
    ) {
        denominations.forEach { denomination ->
            EasyPayButton(
                onClick = {
                    if (denomination == "EXACT") { // exact amount display snack
                        Log.d("TRX", "DenominationButtonsSection: $amount")
                        paymentViewmodel.exactAmount(amount)
                    } else {
                        Log.d(
                            "TRX",
                            "DenominationButtonsSection >>>>>: $denomination amount $amount"
                        )
                        paymentViewmodel.easyPay((denomination.toLong() * 100), amount)
                    }
                },
                modifier = Modifier.weight(1f),
                denomination = denomination,
                textStyle = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}
