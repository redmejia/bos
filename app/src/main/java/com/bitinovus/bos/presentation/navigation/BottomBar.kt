package com.bitinovus.bos.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00

@Composable
fun BottomBar(
    navController: NavHostController,
) {

    val items = listOf(
        BottomBarItem.Scanner,
        BottomBarItem.Pos,
        BottomBarItem.Wallet
    )

    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(BottomAppBarDefaults.windowInsets),
        containerColor = PrimaryBlue60,
        contentColor = PrimaryBlue60
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(route = screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = screen.selectedIcon),
                        contentDescription = stringResource(id = screen.title),
                        tint = PrimaryWhite00
                    )
                },
                label = {
                    Text(
                        if (currentRoute == screen.route)
                            stringResource(id = screen.title) else "",
                        color = PrimaryWhite00,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}