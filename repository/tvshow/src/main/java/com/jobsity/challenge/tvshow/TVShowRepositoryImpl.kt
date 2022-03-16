package com.jobsity.challenge.tvshow

import androidx.annotation.WorkerThread
import com.jobsity.challenge.restapi.RestApi
import com.jobsity.challenge.restapi.data.Episode
import com.jobsity.challenge.restapi.data.TVShowDetail
import com.jobsity.challenge.tvshow.data.EpisodeModel
import com.jobsity.challenge.tvshow.data.Schedule
import com.jobsity.challenge.tvshow.data.ShowStatus
import com.jobsity.challenge.tvshow.data.TVShowModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 *  [TVShowRepository] implementation.
 *  This class is responsible to manage the TV Shows repository and by that, this class is responsible to:
 *  - Fetch TV Series from Internet
 *  - Create and manage internal databases (cache)
 *  - Convert all POJO (database, Rest) into a model appropriate for upper layers (View)
 */
internal class TVShowRepositoryImpl @Inject constructor(private val restApi: RestApi) :
    TVShowRepository {

    @WorkerThread
    override suspend fun getShows(): List<TVShowModel> = withContext(Dispatchers.IO) {
        restApi.getShows().map {
            TVShowModel(it.id, it.name).apply {
                thumbnailUrl = it.images?.medium
            }
        }
    }

    @WorkerThread
    override suspend fun getShow(showId: Long): TVShowModel = withContext(Dispatchers.IO) {
        val episode = restApi.getShow(showId)
        TVShowModel(episode.id, episode.name).apply {
            thumbnailUrl = episode.images?.medium
            imageUrl = episode.images?.original
            summary = episode.summary
            status = episode.toShowStatus()
            type = episode.type
            genres = episode.genres?.map { if(it == "Science-Fiction") "Sci-Fi" else it }
            premiered = episode.premiered
            ended = episode.ended
            rating = episode.rating?.average ?: 0f
            episode.schedule?.let {
                schedule = Schedule(it.time, it.days)
            }
        }
    }

    @WorkerThread
    override suspend fun getEpisodes(showId: Long): List<List<EpisodeModel>> = withContext(Dispatchers.IO) {
        val episodeList = restApi.getEpisodes(showId)
        val result = mutableListOf<List<EpisodeModel>>()
        var season = 1
        var seasonList: List<Episode>
        do {
            seasonList = episodeList.filter {
                it.season == season
            }.sortedBy {
                it.number
            }
            if(seasonList.isNotEmpty()) {
                result.add(seasonList.map {
                    EpisodeModel(it.id, it.name).apply {
                        this.season = it.season
                        this.number = it.number
                    }
                })
                season++
            } else {
                break
            }
        } while(true)
        result
    }

    @WorkerThread
    override suspend fun getEpisode(episodeId: Long): EpisodeModel = withContext(Dispatchers.IO) {
        val episode = restApi.getEpisode(episodeId)
        EpisodeModel(episode.id, episode.name).apply {
            season = episode.season
            number = episode.number
            imageUrl = episode.images?.original
            summary = episode.summary
            airDate = episode.airDate
            airTime = episode.airTime
        }
    }
}

/**
 * Extension to convert the show status returned by the Rest API into a proper Status object
 * which can be shared with upper layers
 */
fun TVShowDetail.toShowStatus() = when(this.status?.lowercase()) {
    "ended" -> ShowStatus.ENDED
    "running" -> ShowStatus.RUNNING
    else -> ShowStatus.UNKNOWN
}

@Module
@InstallIn(SingletonComponent::class)
abstract class TVShowRepositoryProvider {
    @Singleton
    @Binds
    internal abstract fun getTVShowRepository(impl: TVShowRepositoryImpl): TVShowRepository
}