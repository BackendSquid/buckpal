package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.common.AccountId
import backendsquid.buckpal.common.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: AccountId): Money
}
