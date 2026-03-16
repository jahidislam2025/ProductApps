package com.qsoft.feed_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.qsoft.feed_presentation.feed.FeedEvent
import com.qsoft.feed_presentation.feed.FeedScreen
import com.qsoft.feed_presentation.feed.FeedState
import org.junit.Rule
import org.junit.Test

class FeedScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun searchFieldUpdatesText_stateDriven() {
        var searchKey by mutableStateOf("")

        composeTestRule.setContent {
            FeedScreen(
                state = FeedState(searchKey = searchKey),
                onEvent = { event ->
                    if (event is FeedEvent.OnSearchEvent) {
                        searchKey = event.searchKey
                    }
                },
                loadNextPage = {}
            )
        }

        val search = composeTestRule.onNodeWithTag("search_field")

        // debug checks
        search.assertExists()
        search.assertIsDisplayed()
        search.assert(hasSetTextAction())

        // focus then type
        search.performClick()
        search.performTextInput("Laptop")
        composeTestRule.waitForIdle()

        // verify both UI and backing state
        search.assertTextEquals("Laptop")
        assert(searchKey == "Laptop")
    }
}