package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.viewmodels.appsnack.SnackType
import com.bitinovus.bos.presentaion.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentaion.viewmodels.appsnack.AppSnack
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    scannerViewmodel: ScannerViewmodel,
    cartViewmodel: CartViewmodel,
    paymentViewmodel: PaymentViewmodel,
) {
    val navHostController = rememberNavController()

    val productList by cartViewmodel.cartState.collectAsState()
    val cartScreenState by cartViewmodel.cartScreenState.collectAsState()

    // Cart purchase summary is not initialized
    // when the composable is called prevent navigation error
    val cartSummary by cartViewmodel.cartSummaryState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = paymentViewmodel.paymentSnackBarState) {
        paymentViewmodel.paymentSnackBarState.collect { snack ->
            scope.launch {
                snackBarHostState.showSnackbar(AppSnack(snack))
            }
        }
    }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        modifier = modifier.fillMaxSize(),
        containerColor = PrimaryWhite00,
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                val snack = data.visuals as? AppSnack
                Snackbar(
                    modifier = Modifier.padding(all = 4.dp),
                    containerColor = when (snack?.actionLabel) {
                        SnackType.SUCCESS.name -> Color(0xFF28a745)
                        SnackType.ERROR.name -> Color.Red
                        SnackType.WARNING.name -> Color.Yellow
                        else -> PrimaryGrayBase80
                    }
                ) {
                    Text(buildAnnotatedString {
                        when (snack?.actionLabel) {
                            SnackType.SUCCESS.name -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00
                                    )
                                ) { append("Thank You! your Change ") }
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryBlack80,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append("$${snack.message}") }
                            }

                            SnackType.ERROR.name -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(snack.message) }
                            }
                        }
                    })
                }
            }
        },
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue60,
                    titleContentColor = PrimaryWhite00,
                ),
                title = {},
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (cartScreenState) {
                            IconButton(onClick = {
                                cartViewmodel.changeScreenState(state = false)
                                navHostController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "close cart",
                                    tint = PrimaryGrayBase80
                                )
                            }
                        } else {
                            Column { }
                        }


                        Row {
                            Box(
                                // modifier = Modifier.wrapContentSize(),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                IconButton(
                                    enabled = !cartScreenState,
                                    onClick = {
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
            cartSummary = cartSummary,
            paymentViewmodel = paymentViewmodel
        )
    }
}

