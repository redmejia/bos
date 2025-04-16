package com.bitinovus.bos.presentaion.screens.pos.denominationbuttons

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.components.buttons.EasyPayButton
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.R


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DenominationButtonsSection(
    cartViewmodel: CartViewmodel,
    paymentViewmodel: PaymentViewmodel,
    enableButtons: Boolean,
    amount: Long,
    denominations: List<String>,
    maxPerRow: Int = 3,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = maxPerRow
    ) {
        val exact = stringResource(id = R.string.exact).uppercase()
        denominations.forEach { denomination ->
            EasyPayButton(
                enabled = enableButtons,
                onClick = {
                    if (denomination == exact) { // exact amount display snack
                        paymentViewmodel.exactAmount(amount)
                        cartViewmodel.clearCartList()
                    } else {
                        paymentViewmodel.easyPay(
                            denomination = (denomination.toLong() * 100),
                            amount = amount
                        ) { cartViewmodel.clearCartList() }
                    }
                },
                modifier = Modifier.weight(1f),
                denomination = denomination,
                textStyle = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}