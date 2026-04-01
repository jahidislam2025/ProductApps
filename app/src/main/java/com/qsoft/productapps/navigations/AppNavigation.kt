package com.qsoft.productapps.navigations

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qsoft.designsystem.theme.ThemeMode
import com.qsoft.feed_presentation.favorite.FavoriteViewModel
import com.qsoft.feed_presentation.favorite.FavoritesScreen
import com.qsoft.feed_presentation.feed.FeedEvent
import com.qsoft.feed_presentation.feed.FeedScreen
import com.qsoft.feed_presentation.feed.FeedViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    themeMode: ThemeMode
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val bottomNavItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Favorite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ViewModel উপরে একবারই নেওয়া হয়েছে
    val feedViewModel: FeedViewModel = hiltViewModel()

    //hideBottomBar এখানে calculate করা হয়েছে
    val hideBottomBar = currentRoute == "dashboard" &&
            feedViewModel.state.selectedProduct != null

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            if (!hideBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(item.iconRes),
                                    contentDescription = stringResource(item.titleRes)
                                )
                            },
                            label = { Text(stringResource(item.titleRes)) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Dashboard — same feedViewModel instance use হচ্ছে
            composable(BottomNavItem.Dashboard.route) {

                BackHandler(enabled = feedViewModel.state.selectedProduct != null) {
                    feedViewModel.onEvent(FeedEvent.OnBackFromDetailEvent)
                }

                FeedScreen(
                    state = feedViewModel.state,
                    onEvent = feedViewModel::onEvent,
                    loadNextPage = feedViewModel::loadNextPage
                )
            }

            //Favorite Tab
            composable(BottomNavItem.Favorite.route) {
                val favoriteViewModel: FavoriteViewModel = hiltViewModel()

                FavoritesScreen(
                    state = favoriteViewModel.state,
                    onEvent = favoriteViewModel::onEvent
                )
            }
        }
    }
}