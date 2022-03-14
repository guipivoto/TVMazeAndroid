package com.jobsity.challenge.tvshow.data

/**
 * Describes the TV show status like running or already ended
 */
enum class ShowStatus {
    /** TV Show status is unknown */
    UNKNOWN,
    /** TV Show is still being aired on TV */
    RUNNING,
    /** TV Show has already ended and it is no longer being aired */
    ENDED
}