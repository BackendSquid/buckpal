package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.GetAccountBalanceQuery
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import java.time.LocalDateTime

class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort,
) : GetAccountBalanceQuery {

    override fun getAccountBalance(accountId: Account.AccountId): Money = loadAccountPort
        .loadAccount(accountId = accountId, baseLineDate = LocalDateTime.now())
        .calculateBalance()

}
