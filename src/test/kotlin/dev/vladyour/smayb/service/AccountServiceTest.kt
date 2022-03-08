package dev.vladyour.smayb.service

import dev.vladyour.smayb.AbstractIntegrationTest
import dev.vladyour.smayb.dto.HistoryRequest
import dev.vladyour.smayb.dto.HistoryResponse
import dev.vladyour.smayb.dto.TransferRequest
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.ZoneOffset
import java.time.ZonedDateTime

internal class AccountServiceTest: AbstractIntegrationTest() {
    @Autowired
    private lateinit var accountService: AccountService

    private val datetime1 = ZonedDateTime.of(2022, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC)
    private val datetime2 = ZonedDateTime.of(2022, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC)
    private val datetime3 = ZonedDateTime.of(2022, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC)
    private val datetime4 = ZonedDateTime.of(2022, 1, 1, 11, 30, 0, 0, ZoneOffset.UTC)
    private val datetime5 = ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC)
    private val datetime6 = ZonedDateTime.of(2022, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC)

    @Test
    fun `test if exception has been thrown when amount is null`() {
        assertThrows<IllegalStateException> {
            val transaction = TransferRequest(ZonedDateTime.now(), BigDecimal(-26.5))
            accountService.transfer(transaction)
        }
    }

    @Test
    fun `test if exception has been thrown when start is after end`() {
        assertThrows<IllegalStateException> {
            val startDatetime = ZonedDateTime.now()
            val endDatetime = startDatetime.minusMinutes(30)
            val historyRequest = HistoryRequest(startDatetime, endDatetime)
            accountService.history(historyRequest)
        }
    }

    @Test
    fun `test accumulated amount`() {
        val one = BigDecimal.ONE.setScale(2)

        accountService.transfer(TransferRequest(datetime1, one))
        accountService.transfer(TransferRequest(datetime2, one))
        accountService.transfer(TransferRequest(datetime3, one))
        accountService.transfer(TransferRequest(datetime4, one))
        accountService.transfer(TransferRequest(datetime5, one))
        accountService.transfer(TransferRequest(datetime6, one))

        val response1 = accountService.history(HistoryRequest(datetime1, datetime2.minusMinutes(2)))
        val expected1 = listOf(
            HistoryResponse(datetime3, one)
        )
        assertListsAreEqual(expected1, response1)

        val response2 = accountService.history(HistoryRequest(datetime2, datetime5.minusMinutes(2)))
        val expected2 = listOf(
            HistoryResponse(datetime3, one.multiply(BigDecimal(2))),
            HistoryResponse(datetime5, one.multiply(BigDecimal(4)))
        )
        assertListsAreEqual(expected2, response2)
    }

    private fun assertListsAreEqual(expected: List<HistoryResponse>, actual: List<HistoryResponse>) {
        assertIterableEquals(expected, actual)
    }
}
