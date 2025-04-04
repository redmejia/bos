package com.bitinovus.bos.presentaion.screens.scanner.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.components.cart.CartCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite90

@Composable
fun Cart(
    modifier: Modifier = Modifier,
    cart: List<Product>,
    itemsInCart: Int,
    total: Long,
) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        Column {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(items = cart, key = { it.productID }) { product ->
                    CartCard(product)
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent, // Top fade
                                PrimaryWhite90.copy(alpha = 0.1f),
                                PrimaryBlack80.copy(alpha = 0.45f),
                                Color.Transparent // Bottom fade
                            ),
                            startX = 0f,
                            endX = LocalConfiguration.current.screenHeightDp.toFloat()
                        )
                    )
                    .fillMaxWidth(0.6f),

                ) {
                Text(text = "Items: $itemsInCart", color = Color.White)
                Text(text = "Total: ${total / 100.0}", color = Color.White)
            }
        }
    }
}