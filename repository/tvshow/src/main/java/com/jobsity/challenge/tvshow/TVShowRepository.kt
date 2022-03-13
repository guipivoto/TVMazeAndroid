package com.jobsity.challenge.tvshow

import com.jobsity.challenge.tvshow.data.TVShow
import com.jobsity.challenge.tvshow.data.TVShowDetail

/**
 * APIs which allows to retrieve TV Shows from the repository
 */
sealed interface TVShowRepository {

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
}