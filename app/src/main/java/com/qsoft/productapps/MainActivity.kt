package com.qsoft.productapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.qsoft.designsystem.theme.AssessmentTaskTheme
import com.qsoft.designsystem.theme.ThemeMode
import com.qsoft.feed_presentation.feed.FeedScreen
import com.qsoft.feed_presentation.feed.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: MainActivityViewModel = hiltViewModel()
            val feedViewModel: FeedViewModel = hiltViewModel()

            val themeMode by settingsViewModel.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

            LaunchedEffect(Unit) {
                feedViewModel.loadNextPage()
            }

            AssessmentTaskTheme(themeMode = themeMode) {
                FeedScreen(
                    state = feedViewModel.state,
                    onEvent = feedViewModel::onEvent,
                    loadNextPage = feedViewModel::loadNextPage
                )
            }
        }
    }
}
