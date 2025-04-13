package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bitinovus.bos.presentaion.navigation.AppNavigation
import com.bitinovus.bos.presentaion.navigation.BottomBar
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.navigation.AppScreens
import com.bitinovus.bos.presentaion.ui.theme.PrimaryRed00

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    scannerViewmodel: ScannerViewmodel,
    cartViewmodel: CartViewmodel,
) {
    val navHostController = rememberNavController()

    val productList by cartViewmodel.cartState.collectAsState()
    val cartScreenState by cartViewmodel.cartScreenState.collectAsState()

    // Cart purchase summary is not initialized
    // when the composable is called prevent navigation error
    val cartSummary by cartViewmodel.cartSummaryState.collectAsState()

    LaunchedEffect(key1 = productList) {
        if(productList.isNotEmpty()){
            cartViewmodel.updateCartSummary()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PrimaryWhite00,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue60,
                    titleContentColor = PrimaryWhite00,
                ),
                title = {},
                actions = {
                    Row {
                        Box(
                            // modifier = Modifier.wrapContentSize(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            IconButton(onClick = {
                                cartViewmodel.changeScreenState(state = true)
                                navHostController.navigate(route = AppScreens.Cart.name)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = "Localized description",
                                    tint = PrimaryGrayBase80
                                )
                            }
                            if (productList.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 7.dp, end = 7.dp)
                                        .size(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Canvas(modifier = Modifier.fillMaxSize()) {
                                        drawCircle(color = PrimaryRed00)
                                    }
                                }
                            }
                        }
                        IconButton(onClick = { /*Not implemented yet */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_box),
                                contentDescription = "Localized description",
                                tint = PrimaryGrayBase80
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            if (!cartScreenState) {
                BottomBar(navController = navHostController)
            }
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding),
            navHostController = navHostController,
            scannerViewmodel = scannerViewmodel,
            cartViewmodel = cartViewmodel,
            cartSummary = cartSummary
        )
    }
}