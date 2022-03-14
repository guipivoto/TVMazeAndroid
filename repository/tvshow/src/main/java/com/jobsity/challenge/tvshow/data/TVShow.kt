package com.jobsity.challenge.tvshow.data

import com.google.gson.annotations.SerializedName

data class TVShow(val id: Long, val name: String) {
    @SerializedName("image")
    var images: Image? = null
}

data class TVShowDetail(val id: Long, val name: String) {
    @SerializedName("image")
    var images: Image? = null
    var summary: String? = null
    var status: String? = null
    var type: String? = null
    var genres: List<String>? = null
    var premiered: String? = null
    var ended: String? = null
    var officialSite: String? = null
    var runtime: Int = 0
    var averageRuntime: Int = 0
    var weight: Int = 0
    var rating: Rating? = null
    var schedule: Schedule? = null
    var network: Network? = null
}

data class Image(val medium: String, val original: String)

data class Rating(val average: Float)

data class Schedule(val time: String, val days : List<String>)

data class Network(val id : Int, val name : String, val days : List<String>)

data class Country(val name : String, val code : String, val timezone : String)

data class Episode(val id: Long, val name: String) {
    var season : Int = 0
    var number : Int = 0
}

data class EpisodeDetail(val id: Long, val name: String) {
    var season : Int = 0
    var number : Int = 0
    @SerializedName("image")
    var images: Image? = null
    var summary: String? = null
    @SerializedName("airdate")
    var airDate: String? = null
    @SerializedName("airtime")
    var airTime: String? = null
}


