package com.bitinovus.bos.presentaion.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.components.cart.CartCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack95
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = cart, key = { it.productID }) { product ->
                CartCard(
                    product = product,
                    actions = {
                        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {

                            val colors = IconButtonDefaults.iconButtonColors(
                                containerColor = PrimaryBlack95,
                                contentColor = PrimaryWhite00
                            )
                            IconButton(
                                colors = colors,
                                onClick = {
                                    cartViewmodel.decrementItemCounter(
                                        productID = product.productID
                                    )
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_remove),
                                    contentDescription = "Localized description",
                                )
                            }
                            IconButton(
                                colors = colors,
                                onClick = {
                                    cartViewmodel.incrementItemCounter(
                                        productID = product.productID
                                    )
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Localized description",
                                )
                            }
                            IconButton(
                                colors = colors,
                                onClick = {
                                    cartViewmodel.removeItemFromCart(
                                        productID = product.productID
                                    )
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Localized description",
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}