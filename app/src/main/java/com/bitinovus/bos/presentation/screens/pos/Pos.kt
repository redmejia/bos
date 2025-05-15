package com.bitinovus.bos.presentation.screens.pos

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.domain.model.Product
import com.bitinovus.bos.presentation.screens.pos.denominationbuttons.DenominationButtonsSection
import com.bitinovus.bos.presentation.screens.pos.productlist.ProductListSection
import com.bitinovus.bos.presentation.screens.pos.summarysection.SummaryContainer
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.presentation.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.presentation.viewmodels.walletviewmodel.WalletViewmodel
import com.bitinovus.bos.presentation.widget.wallet.WalletReceiver
import java.math.BigDecimal
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

// Checkout Screen
@Composable
fun Pos(
    paymentViewmodel: PaymentViewmodel,
    cartViewmodel: CartViewmodel,
    productList: List<Product>,
    walletViewmodel: WalletViewmodel,
) {
    val context = LocalContext.current

    var isProductListEmpty by remember { mutableStateOf(false) }
    val balance by walletViewmodel.balanceState.collectAsState()

    val summary by cartViewmodel.cartSummaryState.collectAsState()
    LaunchedEffect(key1 = productList) {
        if (productList.isNotEmpty()) {
            cartViewmodel.updateCartSummary()
            isProductListEmpty = false
        } else {
            // is empty
            isProductListEmpty = true
        }
    }

    LaunchedEffect(key1 = balance) {
        if (balance > 0.0) {
            val intent = Intent(context, WalletReceiver::class.java).apply {
                putExtra("balance", balance)
            }
            context.sendBroadcast(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        ProductListSection(paymentViewmodel = paymentViewmodel, productList = productList)
        SummaryContainer(isProductListEmpty = isProductListEmpty, cartSummaryState = summary)
        var text by remember { mutableStateOf("") }
        Column {
            OutlinedTextField(
                enabled = summary.grandTotal > 0 && productList.isNotEmpty(),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                placeholder = { if (text == "") Text(text = stringResource(id = R.string.enter_amount)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = PrimaryBlue60,
                    focusedBorderColor = PrimaryBlue80,
                ),
                singleLine = true,
                maxLines = 1
            )

            FilledTonalButton(
                enabled = summary.grandTotal > 0 && productList.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue80
                ),
                onClick = {
                    // entry in cents
                    val entry = try {
                        BigDecimal(text).multiply(BigDecimal(100)).toLong()
                        // (text.toDouble() * 100).toLong()
                    } catch (_: NumberFormatException) {
                        0L
                    }
                    paymentViewmodel.chargeAmount(
                        order = productList,
                        amountEntered = entry,
                        total = summary.grandTotal
                    ) {
                        text = ""
                        cartViewmodel.clearCartList()
                    }
                }) { Text(text = stringResource(id = R.string.charge).uppercase()) }
        }
        HorizontalDivider()
        val denominationList = listOf(
            "1",
            "5",
            "10",
            "20",
            "50",
            "100",
            stringResource(id = R.string.exact).uppercase()
        )
        val row = 3
        DenominationButtonsSection(
            order = productList,
            paymentViewmodel = paymentViewmodel,
            cartViewmodel = cartViewmodel,
            enableButtons = summary.grandTotal > 0 && productList.isNotEmpty(),
            grandTotal = summary.grandTotal,
            denominations = denominationList,
            maxPerRow = row,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PosPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Pos(productList = emptyList(), cartViewmodel = CartViewmodel = viewModel())
    }
}