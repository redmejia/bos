package com.bitinovus.bos.presentaion.screens.pos.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.components.cart.CartCard

@Composable
fun ProductListSection(productList: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .windowInsetsPadding(WindowInsets.statusBars).imePadding(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(productList) { product ->
            CartCard(product = product)
        }
    }
}