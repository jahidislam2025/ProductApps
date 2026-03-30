package com.qsoft.productapps.navigations

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qsoft.designsystem.theme.ThemeMode
import com.qsoft.feed_presentation.feed.FeedEvent
import com.qsoft.feed_presentation.feed.FeedScreen
import com.qsoft.feed_presentation.feed.FeedViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    themeMode: ThemeMode
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "feed",
            modifier = Modifier
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            composable("feed") {
                val feedViewModel: FeedViewModel = hiltViewModel()

                //এটাই Back button fix করবে
                BackHandler(enabled = feedViewModel.state.selectedProduct != null) {
                    feedViewModel.onEvent(FeedEvent.OnBackFromDetailEvent)
                }

                FeedScreen(
                    state = feedViewModel.state,
                    onEvent = feedViewModel::onEvent,
                    loadNextPage = feedViewModel::loadNextPage
                )
            }
        }
    }
}