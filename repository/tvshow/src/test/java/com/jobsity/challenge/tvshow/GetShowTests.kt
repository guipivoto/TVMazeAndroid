package com.jobsity.challenge.tvshow

import com.jobsity.challenge.restapi.RestApi
import com.jobsity.challenge.restapi.data.Image
import com.jobsity.challenge.restapi.data.Rating
import com.jobsity.challenge.restapi.data.TVShowDetail
import com.jobsity.challenge.tvshow.data.ShowStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


/**
 * Test [TVShowRepository.getShow] API
 */
class GetShowTests {

    @Test
    fun testGetShow_P01() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test")
        }
        val tvShow = TVShowRepositoryImpl(restApi).getShow(1)
        assertNotNull(tvShow)
    }

    @Test
    fun testGetShow_P02() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply { images = null }
        }
        val tvShow = TVShowRepositoryImpl(restApi).getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertNull(tvShow.imageUrl)
        assertNull(tvShow.thumbnailUrl)
    }

    @Test
    fun testGetShow_P03() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                images = Image(
                    "https://www.temp.com/thumbnail.jpg",
                    "https://www.temp.com/image.jpg"
                )
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals("https://www.temp.com/image.jpg", tvShow.imageUrl)
        assertEquals("https://www.temp.com/thumbnail.jpg", tvShow.thumbnailUrl)
    }

    @Test
    fun testGetShow_P04() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                status = "EnDeD"
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals(ShowStatus.ENDED, tvShow.status)
    }

    @Test
    fun testGetShow_P05() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                status = "End"
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals(ShowStatus.UNKNOWN, tvShow.status)
    }

    @Test
    fun testGetShow_P06() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                status = "ended"
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals(ShowStatus.ENDED, tvShow.status)
    }

    @Test
    fun testGetShow_P07() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                status = "running"
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals(ShowStatus.RUNNING, tvShow.status)
    }

    @Test
    fun testGetShow_P08() = runBlocking {
        val restApi = mock<RestApi> {
            on { runBlocking { getShow(1) } } doReturn TVShowDetail(1, "Test").apply {
                status = "ended"
                genres = listOf("Drama", "Science-Fiction", "Thriller")
                premiered = "2013-06-24"
                ended = "2015-09-10"
                schedule = com.jobsity.challenge.restapi.data.Schedule("22:00", listOf("Thursday"))
                images = Image(
                    "https://www.temp.com/thumbnail.jpg",
                    "https://www.temp.com/image.jpg"
                )
                rating = Rating(6.8f)
            }
        }
        val repository = TVShowRepositoryImpl(restApi)
        val tvShow = repository.getShow(1)
        assertEquals(1, tvShow.id)
        assertEquals("Test", tvShow.name)
        assertEquals(ShowStatus.ENDED, tvShow.status)
        assertEquals("2013-06-24", tvShow.premiered)
        assertEquals("2015-09-10", tvShow.ended)
        assertEquals("https://www.temp.com/image.jpg", tvShow.imageUrl)
        assertEquals("https://www.temp.com/thumbnail.jpg", tvShow.thumbnailUrl)
        assertEquals(6.8f, tvShow.rating)
    }
}
