package com.jobsity.challenge.tvshow

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jobsity.challenge.tvshow.data.Episode
import com.jobsity.challenge.tvshow.data.EpisodeDetail
import com.jobsity.challenge.tvshow.data.TVShow
import com.jobsity.challenge.tvshow.data.TVShowDetail
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine


/**
 *  [TVShowRepository] implementation. It must not be used directly. It is public just to allow
 *  dependency injection. Trust on [TVShowRepository] interface only.
 *  This class is responsible to manage the TV Shows repository and by that, this class is responsible to:
 *  - Fetch TV Series from Internet
 *  - Create and manage internal databases (cache)
 */
internal class TVShowRepositoryImpl @Inject constructor(@ApplicationContext val appContext: Context) :
    TVShowRepository {

    override suspend fun getShows(): List<TVShow> {
        return suspendCoroutine { continuation ->
            Volley.newRequestQueue(appContext)
                .add(StringRequest(Request.Method.GET, SHOW_LIST_GET_URL, { response ->
                    try {
                        val objType = object : TypeToken<List<TVShow>>() {}.type
                        continuation.resumeWith(Result.success(Gson().fromJson(response, objType)))
                    } catch (exception: JSONException) {
                        Log.e("Jobsity", "Error while retrieving data", exception)
                        continuation.resumeWith(Result.success(emptyList()))
                    }
                }) { volleyError ->
                    Log.e("Jobsity", "Error while setting up Volley: $volleyError")
                    continuation.resumeWith(Result.success(emptyList()))
                })
        }
    }

    override suspend fun getShow(showId: Int): TVShowDetail {
        return suspendCoroutine { continuation ->
            Volley.newRequestQueue(appContext)
                .add(StringRequest(Request.Method.GET, SHOW_GET_URL + showId, { response ->
                    try {
                        continuation.resumeWith(Result.success(Gson().fromJson(response, TVShowDetail::class.java)))
                    } catch (exception: JSONException) {
                        Log.e("Jobsity", "Error while retrieving data", exception)
                        continuation.resumeWith(Result.success(TVShowDetail(0, "")))
                    }
                }) { volleyError ->
                    Log.e("Jobsity", "Error while setting up Volley: $volleyError")
                    continuation.resumeWith(Result.success(TVShowDetail(0, "")))
                })
        }
    }

    override suspend fun getEpisodes(showId: Int): List<List<Episode>> {
        return suspendCoroutine { continuation ->
            Volley.newRequestQueue(appContext)
                .add(StringRequest(Request.Method.GET, SHOW_GET_URL + showId + EPISODES_GET_PARAMETER, { response ->
                    try {
                        val objType = object : TypeToken<List<Episode>>() {}.type
                        val episodeList : List<Episode> = Gson().fromJson(response, objType)
                        val result = mutableListOf<List<Episode>>()
                        var season = 1
                        var seasonList: List<Episode>
                        do {
                            seasonList = episodeList.filter {
                                it.season == season
                            }.sortedBy {
                                it.number
                            }
                            if(seasonList.isNotEmpty()) {
                                result.add(seasonList)
                                season++
                            } else {
                                break
                            }
                        } while(true)
                        continuation.resumeWith(Result.success(result))
                    } catch (exception: JSONException) {
                        Log.e("Jobsity", "Error while retrieving data", exception)
                        continuation.resumeWith(Result.success(emptyList()))
                    }
                }) { volleyError ->
                    Log.e("Jobsity", "Error while setting up Volley: $volleyError")
                    continuation.resumeWith(Result.success(emptyList()))
                })
        }
    }

    override suspend fun getEpisode(episodeId: Int): EpisodeDetail {
        return suspendCoroutine { continuation ->
            Volley.newRequestQueue(appContext)
                .add(StringRequest(Request.Method.GET, EPISODES_GET_URL + episodeId, { response ->
                    try {
                        continuation.resumeWith(Result.success(Gson().fromJson(response, EpisodeDetail::class.java)))
                    } catch (exception: JSONException) {
                        Log.e("Jobsity", "Error while retrieving data", exception)
                        continuation.resumeWith(Result.success(EpisodeDetail(0, "")))
                    }
                }) { volleyError ->
                    Log.e("Jobsity", "Error while setting up Volley: $volleyError")
                    continuation.resumeWith(Result.success(EpisodeDetail(0, "")))
                })
        }
    }

    companion object {
        /** URL (GET) to retrieve a list of shows */
        const val SHOW_LIST_GET_URL = "https://api.tvmaze.com/shows"

        /** URL (GET) to retrieve a specific show */
        const val SHOW_GET_URL = "https://api.tvmaze.com/shows/"

        /** URL (GET) to retrieve a specific show */
        const val EPISODES_GET_URL = "https://api.tvmaze.com/episodes/"

        /** URL (GET) to retrieve a specific show */
        const val EPISODES_GET_PARAMETER = "/episodes"
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class TVShowRepositoryProvider {
    @Singleton
    @Binds
    internal abstract fun getTVShowRepository(impl: TVShowRepositoryImpl): TVShowRepository
}