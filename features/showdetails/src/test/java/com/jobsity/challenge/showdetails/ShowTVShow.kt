package com.jobsity.challenge.showdetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jobsity.challenge.theme.TVTheme
import com.jobsity.challenge.tvshow.TVShowRepository
import com.jobsity.challenge.tvshow.data.EpisodeModel
import com.jobsity.challenge.tvshow.data.TVShowModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

/**
 * Test [ShowTVShow] Composable function
 */
@RunWith(RobolectricTestRunner::class)
class ShowDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testShowTVShow_P01() {
        runBlocking {
            val repository = mock<TVShowRepository> {
                on { runBlocking { getShow(1) } } doReturn TVShowModel(1, "Test")
                on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                    listOf(
                        EpisodeModel(1, "Test1"),
                        EpisodeModel(2, "Test2")
                    )
                ))
            }
            val tvShow = repository.getShow(1)
            val episodes = repository.getEpisodes(1)

            composeTestRule.setContent {
                TVTheme {
                    ShowTVShow(tvShow, episodes) {}
                }
            }
            composeTestRule.onNodeWithText("Test").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test2").assertIsDisplayed()
            composeTestRule.onNodeWithText("Season 1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Season 2").assertDoesNotExist()
        }
    }

    @Test
    fun testShowTVShow_P02() {
        runBlocking {
            val repository = mock<TVShowRepository> {
                on { runBlocking { getShow(1) } } doReturn TVShowModel(1, "Test")
                on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                    listOf(
                        EpisodeModel(1, "Test1")
                    )
                ))
            }
            val tvShow = repository.getShow(1)
            val episodes = repository.getEpisodes(1)

            composeTestRule.setContent {
                TVTheme {
                    ShowTVShow(tvShow, episodes) {}
                }
            }
            composeTestRule.onNodeWithText("Test").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test2").assertDoesNotExist()
            composeTestRule.onNodeWithText("Season 1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Season 2").assertDoesNotExist()
        }
    }

    @Test
    fun testShowTVShow_P03() {
        runBlocking {
            val repository = mock<TVShowRepository> {
                on { runBlocking { getShow(1) } } doReturn TVShowModel(1, "Test")
                on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                    listOf(
                        EpisodeModel(1, "Test1")
                    ),
                    listOf(
                        EpisodeModel(2, "Test2")
                    )
                ))
            }
            val tvShow = repository.getShow(1)
            val episodes = repository.getEpisodes(1)

            composeTestRule.setContent {
                TVTheme {
                    ShowTVShow(tvShow, episodes) {}
                }
            }
            composeTestRule.onNodeWithText("Test").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test2").assertIsDisplayed()
            composeTestRule.onNodeWithText("Season 1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Season 2").assertIsDisplayed()
        }
    }

    @Test
    fun testShowTVShow_N03() {
        runBlocking {
            val repository = mock<TVShowRepository> {
                on { runBlocking { getShow(1) } } doReturn TVShowModel(1, "Test")
                on { runBlocking { getEpisodes(1) } } doReturn (emptyList())
            }
            val tvShow = repository.getShow(1)
            val episodes = repository.getEpisodes(1)

            composeTestRule.setContent {
                TVTheme {
                    ShowTVShow(tvShow, episodes) {}
                }
            }
            composeTestRule.onNodeWithText("Test").assertIsDisplayed()
            composeTestRule.onNodeWithText("Test1").assertDoesNotExist()
            composeTestRule.onNodeWithText("Test2").assertDoesNotExist()
            composeTestRule.onNodeWithText("Season 1").assertDoesNotExist()
            composeTestRule.onNodeWithText("Season 2").assertDoesNotExist()
        }
    }
}

