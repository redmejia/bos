package com.bitinovus.bos.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
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

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = AppScreens.Wallet.name
    ) {

        composable(
            route = AppScreens.Wallet.name,
            deepLinks = listOf(navDeepLink {
                uriPattern = "bos://wallet"
            })
        ) {
            Wallet(walletViewmodel = walletViewmodel)
        }

        composable(
            route = AppScreens.Scanner.name,
            deepLinks = listOf(navDeepLink { // not implemented yet
                uriPattern = "bos://scanner"
            })
        ) {
            Scanner(
                scannerViewmodel = scannerViewmodel,
                cartViewmodel = cartViewmodel
            )
        }

        // checkout screen
        composable(
            route = AppScreens.Pos.name,
            deepLinks = listOf(navDeepLink { // not implemented yet
                uriPattern = "bos://pos"
            })
        ) {
            Pos(
                walletViewmodel = walletViewmodel,
                productList = productList,
                paymentViewmodel = paymentViewmodel,
                cartViewmodel = cartViewmodel,
            )
        }

        composable(
            route = AppScreens.Cart.name,
            deepLinks = listOf(navDeepLink { // not implemented yet
                uriPattern = "bos://cart"
            })
        ) {
            Cart(
                navHostController = navHostController,
                cartViewmodel = cartViewmodel,
            )
        }

        composable(
            route = AppScreens.History.name,
            deepLinks = listOf(navDeepLink { // not implemented yet
                uriPattern = "bos://history"
            })
        ) {
            History(historyViewmodel = historyViewmodel)
        }
    }
}