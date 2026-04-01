package com.qsoft.productapps.navigations

import com.qsoft.designsystem.R as DesignSystemR

sealed class BottomNavItem(
    val route: String,
    val titleRes: Int,
    val iconRes: Int
) {
    object Dashboard : BottomNavItem(
        route = "dashboard",
        titleRes = com.qsoft.common.R.string.dashboard,
        iconRes = DesignSystemR.drawable.ic_feed
    )

    object Favorite : BottomNavItem(
        route = "favorite",
        titleRes = com.qsoft.common.R.string.favorite,
        iconRes = DesignSystemR.drawable.ic_heart_smile
    )
}

