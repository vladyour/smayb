package dev.vladyour.smayb.dto

import java.math.BigDecimal
import java.time.ZonedDateTime

data class TransferRequest(
    val datetime: ZonedDateTime,
    val amount: BigDecimal
)
