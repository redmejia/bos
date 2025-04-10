package com.bitinovus.bos.presentaion.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Checkout Screen")
            }
        }
    }
}