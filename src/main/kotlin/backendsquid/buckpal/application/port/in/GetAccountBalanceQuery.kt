package backendsquid.buckpal.application.port.`in`

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: Account.AccountId): Money
}