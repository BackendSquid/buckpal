package backendsquid.buckpal.account.application.port.out

import backendsquid.buckpal.account.domain.Account

interface AccountLock {
    fun lockAccount(accountId: Account.AccountId)
    fun releaseAccount(accountId: Account.AccountId)
}
