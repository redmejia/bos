package com.bitinovus.bos.presentaion.navigation

import com.bitinovus.bos.R

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val selectedIcon: Int,
) {

    data object Scanner : BottomBarItem(
        route = "Scanner",
        title = "Scanner",
        selectedIcon = R.drawable.barcode_scanner
    )


    data object Pos : BottomBarItem(
        route = "Pos",
        title = "Pos",
        selectedIcon = R.drawable.point_of_sale
    )

}