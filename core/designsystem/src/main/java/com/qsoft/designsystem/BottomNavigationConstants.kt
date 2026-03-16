package com.qsoft.designsystem

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.qsoft.common.R as CommonR
import com.qsoft.designsystem.R as DesignSystemR

data class BottomNavigationItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)

val BOTTOM_NAVIGATION_ITEM = listOf(
    BottomNavigationItem(
        title = CommonR.string.feed,
        icon = DesignSystemR.drawable.ic_feed,
        route = BottomNavRoute.FEED,
    ),
    BottomNavigationItem(
        title = CommonR.string.favorites,
        icon = DesignSystemR.drawable.ic_heart,
        route = BottomNavRoute.FAVORITES,
    ),
    BottomNavigationItem(
        title = CommonR.string.settings,
        icon = DesignSystemR.drawable.ic_settings,
        route = BottomNavRoute.SETTINGS,
    )
)

object BottomNavRoute {
    const val FEED = "feed"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
}