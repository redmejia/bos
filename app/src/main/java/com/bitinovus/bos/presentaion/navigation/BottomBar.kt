package com.bitinovus.bos.presentaion.navigation

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
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack96
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80

@Composable
fun BottomBar(
    navController: NavHostController,
) {

    val items = listOf(
        BottomBarItem.Scanner,
        BottomBarItem.Pos
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
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = screen.selectedIcon),
                        contentDescription = stringResource(id = screen.title),
                        tint = if (currentRoute == screen.route) PrimaryGrayBase80 else PrimaryBlack96
                    )
                },
                label = {
                    Text(
                        if (currentRoute == screen.route)
                            stringResource(id = screen.title) else "",
                        color = PrimaryGrayBase80,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )

        }
    }

}