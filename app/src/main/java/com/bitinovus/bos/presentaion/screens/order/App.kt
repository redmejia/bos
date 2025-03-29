package com.bitinovus.bos.presentaion.screens.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitinovus.bos.presentaion.screens.scanner.Scanner

@Composable
fun App(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scanner()
    }
}