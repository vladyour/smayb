package dev.vladyour.smayb.dto

import java.math.BigDecimal
import java.time.ZonedDateTime

data class HistoryResponse (
    val datetime: ZonedDateTime,
    val amount: BigDecimal
)
