package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitinovus.bos.presentaion.screens.scanner.Scanner
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel

@Composable
fun App(
    modifier: Modifier = Modifier,
    productViewmodel: ProductViewmodel,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scanner(productViewmodel = productViewmodel)
    }
}