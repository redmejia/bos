package com.bitinovus.bos.presentaion.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentaion.components.cart.CartCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack95
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import androidx.compose.ui.text.TextStyle
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.screens.cart.summarysection.SummarySection
import com.bitinovus.bos.presentaion.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartSummaryState

@Composable
fun Cart(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    cartViewmodel: CartViewmodel,
    productList: List<Product>,
    summary: CartSummaryState, // cart summary
) {
    LaunchedEffect(key1 = productList) {
        if (productList.isNotEmpty()) {
            cartViewmodel.updateCartSummary()
        }
    }

    Column {
        LazyColumn(
            modifier = modifier
                .background(color = PrimaryWhite00)
                .fillMaxHeight(0.80f)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(Modifier.height(5.dp))
            }
            items(items = productList, key = { it.productID }) { product ->
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
            item {
                Spacer(Modifier.height(5.dp))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = "Total",
                trailingText = "$${summary.grandTotal / 100.00}",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = "Total items",
                trailingText = "${summary.itemsInCart}",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            HorizontalDivider()
            Row {
                FilledTonalButton(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue80
                    ),
                    onClick = onClick
                ) { Text("Add More Items", fontSize = 20.sp) }
                FilledTonalButton(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryRed00
                    ),
                    onClick = onClick
                ) { Text("Cancel", fontSize = 20.sp) }
            }
        }
    }
}