package com.jobsity.challenge.tvshow

import com.jobsity.challenge.restapi.RestApi
import com.jobsity.challenge.restapi.data.EpisodeDetail
import com.jobsity.challenge.restapi.data.Image
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


/**
 * Test [TVShowRepository.getEpisode] API
 */
class GetEpisodeTests {

    @Test
    fun testGetShows_P01() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisode(1) } } doReturn EpisodeDetail(1, "Test")
        }
        val episode = TVShowRepositoryImpl(restApi).getEpisode(1)
        assertNotNull(episode)
    }

    @Test
    fun testGetShows_P02() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisode(1) } } doReturn EpisodeDetail(1, "Test").apply {
                images = null
            }
        }
        val episode = TVShowRepositoryImpl(restApi).getEpisode(1)
        assertEquals(1, episode.id)
        assertEquals("Test", episode.name)
        assertNull(episode.imageUrl)
    }

    @Test
    fun testGetShows_P03() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getEpisode(1) } } doReturn EpisodeDetail(1, "Test").apply {
                images = Image(
                    "https://www.temp.com/thumbnail.jpg",
                    "https://www.temp.com/image.jpg"
                )
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val episode = repository.getEpisode(1)
        assertEquals(1, episode.id)
        assertEquals("Test", episode.name)
        assertEquals("https://www.temp.com/image.jpg", episode.imageUrl)
    }
}
