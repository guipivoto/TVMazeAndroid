package com.jobsity.challenge.tvshow

import com.jobsity.challenge.restapi.RestApi
import com.jobsity.challenge.restapi.data.Episode
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

/**
 * Test [TVShowRepository.getEpisodes] API
 */
class GetEpisodesTests {

    @Test
    fun testGetShows_P01() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisodes(1) } } doReturn emptyList()
        }
        val tvShows = TVShowRepositoryImpl(restApi).getEpisodes(1)
        assert(tvShows.isEmpty())
    }

    @Test
    fun testGetShows_P02() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                Episode(1, "Test1").apply {
                    season = 1
                    number = 1
                }))
        }
        val episodes = TVShowRepositoryImpl(restApi).getEpisodes(1)
        assertEquals(1, episodes.size)
        assertEquals(1, episodes[0].size)
        assertEquals(1, episodes[0][0].id)
        assertEquals("Test1", episodes[0][0].name)
        assertEquals(1, episodes[0][0].season)
        assertEquals(1, episodes[0][0].number)
    }

    @Test
    fun testGetShows_P03() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                Episode(1, "Test1").apply {
                    season = 1
                    number = 1
                },
                Episode(2, "Test2").apply
                {
                    season = 1
                    number = 2
                }))
        }
        val episodes = TVShowRepositoryImpl(restApi).getEpisodes(1)
        assertEquals(1, episodes.size)
        assertEquals(2, episodes[0].size)
        assertEquals(1, episodes[0][0].id)
        assertEquals("Test1", episodes[0][0].name)
        assertEquals(1, episodes[0][0].season)
        assertEquals(1, episodes[0][0].number)
        assertEquals(2, episodes[0][1].id)
        assertEquals("Test2", episodes[0][1].name)
        assertEquals(1, episodes[0][1].season)
        assertEquals(2, episodes[0][1].number)
    }

    @Test
    fun testGetShows_P04() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisodes(1) } } doReturn (listOf(
                Episode(1, "Test1").apply {
                    season = 1
                    number = 1
                },
                Episode(2, "Test2").apply
                {
                    season = 2
                    number = 1
                }))
        }
        val episodes = TVShowRepositoryImpl(restApi).getEpisodes(1)
        assertEquals(2, episodes.size)
        assertEquals(1, episodes[0].size)
        assertEquals(1, episodes[1].size)
        assertEquals(1, episodes[0][0].id)
        assertEquals("Test1", episodes[0][0].name)
        assertEquals(1, episodes[0][0].season)
        assertEquals(1, episodes[0][0].number)
        assertEquals(2, episodes[1][0].id)
        assertEquals("Test2", episodes[1][0].name)
        assertEquals(2, episodes[1][0].season)
        assertEquals(1, episodes[1][0].number)
    }
}
