package com.jobsity.challenge.restapi

import com.jobsity.challenge.restapi.data.Episode
import com.jobsity.challenge.restapi.data.EpisodeDetail
import com.jobsity.challenge.restapi.data.TVShow
import com.jobsity.challenge.restapi.data.TVShowDetail

sealed interface RestApi {

    /**
     * Return a list of TV shows
     */
    suspend fun getShows(): List<TVShow>

    /**
     * Return detailed info about a show
     * @param showId Show ID on TVMaze
     * @return [TVShowDetail] object containing all details about a show
     */
    suspend fun getShow(showId: Int): TVShowDetail

    /**
     * Return the list of episodes for a given series separated by season
     * @param showId Show ID on TVMaze
     * @return A list of episodes for each season
     */
    suspend fun getEpisodes(showId: Int): List<Episode>

    /**
     * Return detailed info about an episode
     * @param episodeId Episode ID on TVMaze
     * @return [EpisodeDetail] object containing all details about an episode
     */
    suspend fun getEpisode(episodeId: Int): EpisodeDetail
}