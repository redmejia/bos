package com.bitinovus.bos.presentaion.screens.scanner.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.components.cart.CartCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack95
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack96
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite80
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel

@Composable
fun Cart(
    cartViewmodel: CartViewmodel,
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
                    CartCard(
                        product = product,
                        footerContent = {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                val shape = RoundedCornerShape(10.dp)

                                FilledTonalButton(
                                    shape = shape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryBlack96
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        cartViewmodel.decrementItemCounter(productID = product.productID)
                                    }) {
                                    Icon(
                                        painterResource(id = R.drawable.outline_remove),
                                        contentDescription = null
                                    )
                                }

                                FilledTonalButton(
                                    shape = shape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryBlack95
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        cartViewmodel.incrementItemCounter(productID = product.productID)
                                    }) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }

                                FilledTonalButton(
                                    shape = shape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        cartViewmodel.removeItemFromCart(productID = product.productID)
                                    }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                // Color.Transparent, // Top fade
                                PrimaryBlack80,
                                PrimaryWhite80.copy(alpha = 0.50f),
                                // Color.Transparent // Bottom fade
                            ),
                            startX = 0f,
                            endX = LocalConfiguration.current.screenHeightDp.toFloat()
                        )
                    )
                    .fillMaxWidth(),

                ) {
                Text(text = "Items: $itemsInCart", color = Color.White)
                Text(text = "Total: ${total / 100.0}", color = Color.White)
            }
        }
    }
}