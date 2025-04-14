package com.bitinovus.bos.presentaion.screens.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
@Composable
fun Pos() {
    val productList: List<Product> = emptyList()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProductListSection(productList)
        SummaryContainer()
        var text by remember { mutableStateOf("") }
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Enter Amount") },
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue80
                ),
                onClick = {}) {
                Text("Charge")
            }
        }
        HorizontalDivider()
        val denominationList = listOf("1", "5", "10", "20", "50", "100", "exact".uppercase())
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
        Pos()
    }
}