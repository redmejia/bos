package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bitinovus.bos.presentaion.navigation.AppNavigation
import com.bitinovus.bos.presentaion.navigation.BottomBar
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.navigation.AppScreens
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.viewmodels.appsnack.SnackStateType
import com.bitinovus.bos.presentaion.viewmodels.appsnack.SnackMessageType
import com.bitinovus.bos.presentaion.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentaion.ui.theme.PrimaryTail00
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite90
import com.bitinovus.bos.presentaion.ui.theme.PrimaryYellow00
import com.bitinovus.bos.presentaion.viewmodels.appsnack.AppSnack
import com.bitinovus.bos.presentaion.viewmodels.historyviewmodel.HistoryViewmodel
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    cartViewmodel: CartViewmodel = hiltViewModel(),
    paymentViewmodel: PaymentViewmodel = hiltViewModel(),
    historyViewmodel: HistoryViewmodel = hiltViewModel(),
) {

    val navHostController = rememberNavController()

    val productList by cartViewmodel.cartState.collectAsState()
    val cartScreenState by cartViewmodel.cartScreenState.collectAsState()
    val historyScreenState by historyViewmodel.historyScreenState.collectAsState()
    val history by historyViewmodel.orderHistoryState.collectAsState()
    val isWriting by historyViewmodel.reportWriteState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val paymentState by paymentViewmodel.paymentState.collectAsState()

    LaunchedEffect(key1 = paymentViewmodel.paymentSnackBarState) {
        paymentViewmodel.paymentSnackBarState.collect { snack ->
            scope.launch {
                snackBarHostState.showSnackbar(AppSnack(snack))
            }
        }
    }

    // close history screen after write report
    LaunchedEffect(key1 = isWriting) {
        if (!isWriting) {
            historyViewmodel.changeHistoryScreenState(state = false)
            navHostController.popBackStack()
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
                        SnackStateType.SUCCESS.name -> PrimaryTail00
                        SnackStateType.ERROR.name -> PrimaryRed00
                        SnackStateType.WARNING.name -> PrimaryYellow00
                        else -> PrimaryGrayBase80
                    }
                ) {
                    Text(buildAnnotatedString {
                        when (snack?.message) {
                            SnackMessageType.TRX_SUCCESS.value -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(stringResource(id = R.string.trx_successful)) }
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryBlack80,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(" $${paymentState.change / 100.00}") }
                            }

                            SnackMessageType.TRX_NO_ACT.value -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(stringResource(id = R.string.no_action)) }
                            }

                            SnackMessageType.ERROR_AMT.value -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(stringResource(id = R.string.error_amount)) }
                            }

                            SnackMessageType.ERROR_ENTRY_AMT.value -> {
                                withStyle(
                                    style = SpanStyle(
                                        color = PrimaryWhite00,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append(stringResource(id = R.string.error_entered_amount)) }
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
                                cartViewmodel.changeCartScreenState(state = false)
                                navHostController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "close cart",
                                    tint = PrimaryGrayBase80
                                )
                            }
                        } else if (historyScreenState) {
                            IconButton(onClick = {
                                historyViewmodel.changeHistoryScreenState(state = false)
                                navHostController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "close history",
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
                                    enabled = !cartScreenState && !historyScreenState,
                                    onClick = {
                                        cartViewmodel.changeCartScreenState(state = true)
                                        navHostController.navigate(route = AppScreens.Cart.name)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.ShoppingCart,
                                        contentDescription = null,
                                        tint = if (!cartScreenState && historyScreenState) PrimaryWhite90
                                        else PrimaryWhite00
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
                            AnimatedVisibility(
                                visible = history.isNotEmpty(),
                                enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                                exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
                            ) {
                                IconButton(
                                    enabled = !historyScreenState && !cartScreenState,
                                    onClick = {
                                        historyViewmodel.changeHistoryScreenState(state = true)
                                        navHostController.navigate(route = AppScreens.History.name)
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_list),
                                        contentDescription = null,
                                        tint = if (!historyScreenState && cartScreenState) PrimaryWhite90
                                        else PrimaryWhite00
                                    )
                                }
                            }
                        }
                    }
                },
            )
        },
        bottomBar = {
            if (!cartScreenState && !historyScreenState) {
                BottomBar(navController = navHostController)
            }
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding),
            navHostController = navHostController,
        )
    }
}

