package com.jobsity.challenge.tvshow

import androidx.annotation.WorkerThread
import com.jobsity.challenge.tvshow.data.EpisodeModel
import com.jobsity.challenge.tvshow.data.TVShowModel

/**
 * APIs responsible to provide data Models to upper classes.
 * Implementations of this interface should be responsible to consult RestAPI, Databases and Cache etc
 */
sealed interface TVShowRepository {

    /**
     * Return a list of TV shows
     */
    @WorkerThread
    suspend fun getShows(): List<TVShowModel>

    /**
     * Return detailed info about a show
     * @param showId Show ID on TVMaze
     * @return [TVShowModel] object containing all details about a show
     */
    @WorkerThread
    suspend fun getShow(showId: Long): TVShowModel

    /**
     * Return the list of episodes for a given series separated by season
     * @param showId Show ID on TVMaze
     * @return A list of episodes for each season
     */
    @WorkerThread
    suspend fun getEpisodes(showId: Long): List<List<EpisodeModel>>
    /**
     * Return detailed info about an episode
     * @param episodeId Episode ID on TVMaze
     * @return [EpisodeModel] object containing all details about an episode
     */
    @WorkerThread
    suspend fun getEpisode(episodeId: Long): EpisodeModel
}