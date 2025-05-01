package com.bitinovus.bos.presentaion.screens.pos.denominationbuttons

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
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.TrxType
import com.bitinovus.bos.presentaion.viewmodels.walletviewmodel.WalletViewmodel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DenominationButtonsSection(
    cartViewmodel: CartViewmodel,
    paymentViewmodel: PaymentViewmodel,
    walletViewmodel: WalletViewmodel,
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
                        walletViewmodel.confirmTransaction(
                            amount = amount,
                            trxType = TrxType.CASH
                        )
                        paymentViewmodel.exactAmount(amount = amount) // no action need
                        cartViewmodel.clearCartList()
                    } else {

                        paymentViewmodel.easyPay(
                            denomination = (denomination.toLong() * 100),
                            amount = amount
                        )
                        // execute after check if denomination is bigger than amount
                        walletViewmodel.confirmTransaction(
                            amount = amount,
                            trxType = TrxType.CASH
                        )
                        cartViewmodel.clearCartList()

                    }
                },
                modifier = Modifier.weight(1f),
                denomination = denomination,
                textStyle = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}