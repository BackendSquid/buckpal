package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.GetAccountBalanceQuery
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAccountBalanceController(
    private val getAccountBalanceQuery: GetAccountBalanceQuery
) {

    @GetMapping("/accounts/balance/{accountId}")
    fun getAccountBalance(
        @PathVariable("accountId") accountId: Long,
    ): Money {
        return getAccountBalanceQuery.getAccountBalance(
            accountId = Account.AccountId(accountId) // 여기서 Account 도메인을 알아야 하나
        )
    }
}
