package dev.vladyour.smayb.repository

import dev.vladyour.smayb.dto.HistoryResponse
import dev.vladyour.smayb.util.ACCUMULATED_AMOUNT_QUERY
import dev.vladyour.smayb.util.INSERT_QUERY
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Repository
class AccountRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
): AccountRepository {

    override fun saveTransaction(datetime: ZonedDateTime, amount: BigDecimal): Boolean {
        val params = mapOf(
            "datetime" to Timestamp.from(datetime.toInstant()),
            "amount" to amount
        )

        val insertedRows = jdbcTemplate.update(INSERT_QUERY, params)
        return insertedRows > 0
    }

    override fun getHourTotalAmountBetween(
        startDatetime: ZonedDateTime,
        endDatetime: ZonedDateTime
    ): List<HistoryResponse> {
        val params = mapOf(
            "startDatetime" to Timestamp.from(startDatetime.toInstant()),
            "endDatetime" to Timestamp.from(endDatetime.toInstant())
        )

        return jdbcTemplate.query(ACCUMULATED_AMOUNT_QUERY, params) { rs, _ ->
            val timestamp = rs.getTimestamp(1)
            val datetime = ZonedDateTime
                .of(timestamp.toLocalDateTime(), ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)

            HistoryResponse(datetime, rs.getBigDecimal(2))
        }
    }
}
