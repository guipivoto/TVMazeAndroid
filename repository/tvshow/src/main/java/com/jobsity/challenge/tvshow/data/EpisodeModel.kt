package com.jobsity.challenge.tvshow.data

/**
 * Episode Model Class.
 * This class is intended to be written by TV Show Repository and it is exported to upper layers
 */
data class EpisodeModel(val id: Long, val name: String) {
    var season : Int = 0
        internal set
    var number : Int = 0
        internal set
    var imageUrl: String? = null
        internal set
    var summary: String? = null
        internal set
    var airDate: String? = null
        internal set
    var airTime: String? = null
        internal set
}