package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.GetAccountBalanceQuery
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.common.AccountId
import backendsquid.buckpal.common.Money
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort,
) : GetAccountBalanceQuery {

    override fun getAccountBalance(accountId: AccountId): Money = loadAccountPort
        .loadAccount(accountId = accountId, baseLineDate = LocalDateTime.now())
        .calculateBalance()

}
