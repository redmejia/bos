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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    scannerViewmodel: ScannerViewmodel,
    cartViewmodel: CartViewmodel,
) {
    val navHostController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PrimaryGrayBase80,
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
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = "Localized description",
                                    tint = PrimaryGrayBase80
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(top = 7.dp, end = 7.dp)
                                    .size(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                val purpleColor = Color.Red
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawCircle(color = purpleColor)
                                }
                            }

                        }
                        IconButton(onClick = { }) {
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
            BottomBar(navController = navHostController)
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding),
            scannerViewmodel = scannerViewmodel, cartViewmodel = cartViewmodel,
            navHostController = navHostController
        )
    }
}