package com.bitinovus.bos.presentation.screens.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentation.components.card.CartCard
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlack95
import com.bitinovus.bos.R
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartViewmodel
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import com.bitinovus.bos.domain.model.Product
import com.bitinovus.bos.presentation.screens.cart.cartsummarysection.CartSummarySection
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartSummaryState

@Composable
fun Cart(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    cartViewmodel: CartViewmodel,
    cart: List<Product>,
    summary: CartSummaryState,
) {

    // prevent system back navigation go to previous screen
    BackHandler(enabled = true) {}

    LaunchedEffect(key1 = cart) {
        if (cart.isNotEmpty()) {
            cartViewmodel.updateCartSummary()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryWhite00)
    ) {
        if (cart.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(id = R.string.empty_cart),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )

            }
            CartSummarySection(
                cart = cart,
                navHostController = navHostController,
                cartViewmodel = cartViewmodel,
                summary = summary
            )

        } else {
            LazyColumn(
                modifier = modifier
                    .background(color = PrimaryWhite00)
                    .fillMaxSize()
                    .padding(horizontal = 6.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item {
                    Spacer(Modifier.height(5.dp))
                }
                items(items = cart, key = { it.productID }) { product ->
                    CartCard(
                        product = product,
                        content = {
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
                                        contentDescription = null,
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
                                        contentDescription = null,
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
                                        contentDescription = null,
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
            CartSummarySection(
                cart = cart,
                navHostController = navHostController,
                cartViewmodel = cartViewmodel,
                summary = summary,
            )
        }
    }
}