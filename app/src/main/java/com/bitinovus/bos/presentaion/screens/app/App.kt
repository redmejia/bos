package com.bitinovus.bos.presentaion.screens.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitinovus.bos.presentaion.screens.scanner.Scanner
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel

@Composable
fun App(
    modifier: Modifier = Modifier,
    productViewmodel: ProductViewmodel = viewModel()
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scanner()
    }
}