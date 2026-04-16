package com.qsoft.productapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.qsoft.designsystem.theme.AssessmentTaskTheme
import com.qsoft.designsystem.theme.ThemeMode
import com.qsoft.productapps.navigations.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        enableEdgeToEdge()
        setContent {
            val settingsViewModel: MainActivityViewModel = hiltViewModel()
            val themeMode by settingsViewModel.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

            AssessmentTaskTheme(themeMode = themeMode) {
                AppNavigation(themeMode = themeMode)
            }
        }
    }
}