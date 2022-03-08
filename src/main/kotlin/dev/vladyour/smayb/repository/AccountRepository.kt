package dev.vladyour.smayb.repository

import dev.vladyour.smayb.dto.HistoryResponse
import java.math.BigDecimal
import java.time.ZonedDateTime

interface AccountRepository {
    fun saveTransaction(datetime: ZonedDateTime, amount: BigDecimal): Boolean
    fun getHourTotalAmountBetween(startDatetime: ZonedDateTime, endDatetime: ZonedDateTime): List<HistoryResponse>
}
