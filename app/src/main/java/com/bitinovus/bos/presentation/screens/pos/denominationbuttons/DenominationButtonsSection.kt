package com.bitinovus.bos.presentation.screens.pos.denominationbuttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentation.components.buttons.EasyPayButton
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentation.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.domain.model.Product


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DenominationButtonsSection(
    order: List<Product>,
    cartViewmodel: CartViewmodel,
    paymentViewmodel: PaymentViewmodel,
    enableButtons: Boolean,
    grandTotal: Long,
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
                        paymentViewmodel.exactAmount(
                            order = order,
                            grandTotal = grandTotal,
                            // amount = grandTotal // exact as total
                        ) // no action need
                        cartViewmodel.clearCartList()
                    } else {
                        paymentViewmodel.easyPay(
                            order = order,
                            denominationSelected = (denomination.toLong() * 100),
                            grandTotal = grandTotal,
                        ){
                            cartViewmodel.clearCartList()
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                denomination = denomination,
                textStyle = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}