package dev.vladyour.smayb.dto

import java.time.ZonedDateTime

data class HistoryRequest(
    val startDatetime: ZonedDateTime,
    val endDatetime: ZonedDateTime
)
