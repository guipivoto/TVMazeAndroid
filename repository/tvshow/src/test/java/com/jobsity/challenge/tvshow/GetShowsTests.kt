package com.jobsity.challenge.tvshow

import com.jobsity.challenge.restapi.RestApi
import com.jobsity.challenge.restapi.data.Image
import com.jobsity.challenge.restapi.data.TVShow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


/**
 * Test [TVShowRepository.getShows] API
 */
class GetShowsTests {

    @Test
    fun testGetShows_P01() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShows(1) } } doReturn emptyList()
        }
        val tvShows = TVShowRepositoryImpl(restApi).getShows(1)
        assert(tvShows.isEmpty())
    }

    @Test
    fun testGetShows_P02() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShows(1) } } doReturn (listOf(TVShow(1, "Test").apply {
                images = null
            }))
        }
        val tvShows = TVShowRepositoryImpl(restApi).getShows(1)
        assertEquals(1, tvShows.size)
        assertEquals(1, tvShows[0].id)
        assertEquals("Test", tvShows[0].name)
        assertNull(tvShows[0].imageUrl)
        assertNull(tvShows[0].thumbnailUrl)
    }

    @Test
    fun testGetShows_P03() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShows(1) } } doReturn (listOf(TVShow(1, "Test").apply {
                images = Image(
                    "https://www.temp.com/thumbnail.jpg",
                    "https://www.temp.com/image.jpg"
                )
            }))
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShows = repository.getShows(1)
        assertEquals(1, tvShows.size)
        assertEquals(1, tvShows[0].id)
        assertEquals("Test", tvShows[0].name)
        assertNull(tvShows[0].imageUrl)
        assertEquals("https://www.temp.com/thumbnail.jpg", tvShows[0].thumbnailUrl)
    }
}
