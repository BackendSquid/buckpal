package backendsquid.buckpal.application.service

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.application.port.`in`.GetAccountBalanceQuery
import backendsquid.buckpal.application.port.out.LoadAccountPort
import java.time.LocalDateTime

class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort
) : GetAccountBalanceQuery {

    override fun getAccountBalance(accountId: Account.AccountId): Money =
        loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance()
}