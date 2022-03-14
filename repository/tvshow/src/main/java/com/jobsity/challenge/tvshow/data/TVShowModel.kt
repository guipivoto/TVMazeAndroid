package com.jobsity.challenge.tvshow.data

/**
 * Model class for TV Show. It's properties can be written only by Repository module
 */
data class TVShowModel(val id: Long, val name: String) {
    var thumbnailUrl: String? = null
        internal set
    var imageUrl: String? = null
        internal set
    var summary: String? = null
        internal set
    var status: ShowStatus = ShowStatus.UNKNOWN
        internal set
    var type: String? = null
        internal set
    var genres: List<String>? = null
        internal set
    var premiered: String? = null
        internal set
    var ended: String? = null
        internal set
    var schedule: Schedule? = null
        internal set
}