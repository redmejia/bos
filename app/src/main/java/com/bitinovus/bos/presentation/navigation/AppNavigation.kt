package com.bitinovus.bos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bitinovus.bos.presentation.screens.cart.Cart
import com.bitinovus.bos.presentation.screens.history.History
import com.bitinovus.bos.presentation.screens.pos.Pos
import com.bitinovus.bos.presentation.screens.scanner.Scanner
import com.bitinovus.bos.presentation.screens.wallet.Wallet
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentation.viewmodels.historyviewmodel.HistoryViewmodel
import com.bitinovus.bos.presentation.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.presentation.viewmodels.scannerviewmodel.ScannerViewmodel
import com.bitinovus.bos.presentation.viewmodels.walletviewmodel.WalletViewmodel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    scannerViewmodel: ScannerViewmodel = hiltViewModel(),
    cartViewmodel: CartViewmodel = hiltViewModel(),
    paymentViewmodel: PaymentViewmodel = hiltViewModel(),
    walletViewmodel: WalletViewmodel = hiltViewModel(),
    historyViewmodel: HistoryViewmodel = hiltViewModel(),
) {
    val productList by cartViewmodel.cartState.collectAsState()
    val cartSummary by cartViewmodel.cartSummaryState.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = AppScreens.Scanner.name
    ) {
        composable(route = AppScreens.Scanner.name) {
            Scanner(
                scannerViewmodel = scannerViewmodel,
                cartViewmodel = cartViewmodel
            )
        }

        // checkout screen
        composable(route = AppScreens.Pos.name) {
            Pos(
                walletViewmodel = walletViewmodel,
                productList = productList,
                paymentViewmodel = paymentViewmodel,
                cartViewmodel = cartViewmodel,
            )
        }

        composable(route = AppScreens.Wallet.name) {
            Wallet(walletViewmodel = walletViewmodel)
        }

        composable(route = AppScreens.Cart.name) {

            Cart(
                navHostController = navHostController,
                cartViewmodel = cartViewmodel,
                productList = productList,
                summary = cartSummary
            )
        }

        composable(route = AppScreens.History.name) {
            History(historyViewmodel = historyViewmodel)
        }
    }
}