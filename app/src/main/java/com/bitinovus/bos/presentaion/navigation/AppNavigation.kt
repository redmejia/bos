package com.bitinovus.bos.presentaion.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bitinovus.bos.presentaion.screens.pos.Pos
import com.bitinovus.bos.presentaion.screens.scanner.Scanner
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    scannerViewmodel: ScannerViewmodel = viewModel(),
    cartViewmodel: CartViewmodel = viewModel(),
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = AppScreens.Scanner.name
    ) {
        composable(route = AppScreens.Scanner.name) {
            Scanner(scannerViewmodel = scannerViewmodel, cartViewmodel = cartViewmodel)
        }

        // checkout screen
        composable(route = AppScreens.Pos.name) {
            Pos()
        }
    }
}