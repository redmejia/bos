package com.bitinovus.bos.presentation.navigation

import com.bitinovus.bos.R

sealed class BottomBarItem(
    val route: String,
    val title: Int,
    val selectedIcon: Int,
) {

    data object Scanner : BottomBarItem(
        route = "Scanner",
        title = R.string.scanner,
        selectedIcon = R.drawable.barcode_scanner
    )

    // Pos - Checkout screen
    data object Pos : BottomBarItem(
        route = "Pos",
        title = R.string.checkout,
        selectedIcon = R.drawable.point_of_sale
    )

    data object Wallet : BottomBarItem(
        route = "Wallet",
        title = R.string.wallet,
        selectedIcon = R.drawable.account_balance_wallet
    )

}