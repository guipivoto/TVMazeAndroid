package com.jobsity.challenge.tvshow

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    companion object {
        /** URL (GET) to retrieve a list of shows */
        const val SHOW_LIST_GET_URL = "https://api.tvmaze.com/shows"

        /** URL (GET) to retrieve a specific show */
        const val SHOW_GET_URL = "https://api.tvmaze.com/shows/"
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class TVShowRepositoryProvider {
    @Singleton
    @Binds
    internal abstract fun getTVShowRepository(impl: TVShowRepositoryImpl): TVShowRepository
}