package dev.vladyour.smayb.service

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import dev.vladyour.smayb.dto.HistoryRequest
import dev.vladyour.smayb.dto.HistoryResponse
import dev.vladyour.smayb.dto.TransferRequest
import dev.vladyour.smayb.repository.AccountRepository
import java.math.BigDecimal

@DgsComponent
class AccountService(
    private val accountRepository: AccountRepository
) {
    @DgsQuery
    fun history(@InputArgument historyRequest: HistoryRequest): List<HistoryResponse> {
        if (historyRequest.startDatetime.isAfter(historyRequest.endDatetime)) {
            throw IllegalStateException("'startDatetime' should be before 'endDatetime'")
        }
        return accountRepository.getHourTotalAmountBetween(historyRequest.startDatetime, historyRequest.endDatetime)
    }

    @DgsMutation
    fun transfer(@InputArgument transferRequest: TransferRequest): Boolean {
        if (transferRequest.amount <= BigDecimal.ZERO) {
            throw IllegalStateException("Amount should be greater than zero")
        }
        return accountRepository.saveTransaction(transferRequest.datetime, transferRequest.amount)
    }
}
