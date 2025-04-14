package com.bitinovus.bos.presentaion.screens.pos

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
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.screens.pos.denominationbuttons.DenominationButtonsSection
import com.bitinovus.bos.presentaion.screens.pos.productlist.ProductListSection
import com.bitinovus.bos.presentaion.screens.pos.summarysection.SummaryContainer
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.R

// Checkout Screen
@Composable
fun Pos(
    cartViewmodel: CartViewmodel,
    productList: List<Product>,
) {

    var isProductListEmpty by remember { mutableStateOf(false) }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProductListSection(productList)
        SummaryContainer(isProductListEmpty = isProductListEmpty, cartSummaryState = summary)
        var text by remember { mutableStateOf("") }
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(text = stringResource(id = R.string.enter_amount)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = PrimaryBlue60,
                    focusedBorderColor = PrimaryBlue80,
                ),
                singleLine = true,
                maxLines = 1
            )
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue80
                ),
                onClick = {}) {
                Text(text = stringResource(id = R.string.charge).uppercase())
            }
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
        DenominationButtonsSection(denominationList, maxPerRow = row)
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