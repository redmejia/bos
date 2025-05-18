package com.bitinovus.bos.presentation.screens.cart.cartsummarysection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bitinovus.bos.R
import com.bitinovus.bos.domain.model.Product
import com.bitinovus.bos.presentation.components.summarysection.SummarySection
import com.bitinovus.bos.presentation.navigation.AppScreens
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentation.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentation.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartViewmodel
import androidx.compose.runtime.getValue

@Composable
fun CartSummarySection(
    navHostController: NavHostController,
    cartViewmodel: CartViewmodel,
    cart: List<Product>,
//    summary: CartSummaryState,
) {
    val summary by cartViewmodel.cartSummaryState.collectAsState()
    Column(
        modifier = Modifier
            .background(
                color = PrimaryGrayBase80,
                RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = stringResource(id = R.string.total),
                trailingText = "$${
                    if (cart.isNotEmpty()) summary.grandTotal / 100.00 else 0.0
                }",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                leadingText = stringResource(id = R.string.total_items),
                trailingText = "${if (cart.isNotEmpty()) summary.itemsInCart else 0}",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            FilledTonalButton(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue80
                ),
                onClick = {
                    cartViewmodel.changeCartScreenState(state = false)
                    // navigate to scanner to add more items
                    navHostController.navigate(route = AppScreens.Scanner.name) {
                        popUpTo(route = AppScreens.Cart.name) {
                            inclusive = true
                            saveState = false
                        }
                    }
                }
            ) { Text(text = stringResource(id = R.string.add_items)) }
            FilledTonalButton(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed00
                ),
                onClick = {
                    cartViewmodel.changeCartScreenState(state = false)
                    cartViewmodel.clearCartList()
                    // back to previous screen
                    navHostController.popBackStack()
                }
            ) { Text(text = stringResource(id = R.string.cancel)) }
        }
    }
}