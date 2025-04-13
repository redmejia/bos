package com.bitinovus.bos.presentaion.screens.pos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.screens.pos.denominationbuttons.DenominationButtonsSection
import com.bitinovus.bos.presentaion.screens.pos.productlist.ProductListSection
import com.bitinovus.bos.presentaion.screens.pos.summarysection.SummaryContainer
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue80

// Checkout Screen
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Pos() {
    val productList: List<Product> = emptyList()
    Column(
        modifier = Modifier
            .padding(4.dp)

    ) {
        ProductListSection(productList)
        Spacer(Modifier.height(10.dp))
        SummaryContainer()
        Spacer(Modifier.height(10.dp))
        val denominationList = listOf("1", "5", "10", "20", "50", "100", "exact".uppercase())
        val row = 3
        DenominationButtonsSection(denominationList, maxPerRow = row)
        var text by remember { mutableStateOf("") }
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = { },
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
        }

        // Charge button here
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