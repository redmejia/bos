package com.bitinovus.bos.presentaion.screens.pos.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.domain.model.Product
import com.bitinovus.bos.presentaion.components.summarysection.SummarySection
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.R

@Composable
fun ProductListSection(
    paymentViewmodel: PaymentViewmodel,
    productList: List<Product>,
) {
    val paymentState by paymentViewmodel.paymentState.collectAsState()
    // Keep last transaction record
    if (productList.isEmpty() && paymentState.trxExecuted) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append(stringResource(id = R.string.change))
                        append("\n\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) { append("$${paymentState.change / 100.00}") }
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            // .windowInsetsPadding(WindowInsets.statusBars)
            //.imePadding(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(productList, key = { it.productID }) { product ->
                SummarySection(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    leadingText = product.name,
                    trailingText = "${product.items * product.price / 100.0}"
                )
            }
        }
    }

}