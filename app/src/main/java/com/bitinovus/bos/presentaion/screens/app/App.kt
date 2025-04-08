package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitinovus.bos.presentaion.screens.scanner.Scanner
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel

@Composable
fun App(
    modifier: Modifier = Modifier,
    scannerViewmodel: ScannerViewmodel,
    cartViewmodel: CartViewmodel,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scanner(scannerViewmodel = scannerViewmodel, cartViewmodel = cartViewmodel)
    }
}