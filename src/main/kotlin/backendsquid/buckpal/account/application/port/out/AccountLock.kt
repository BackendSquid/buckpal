package backendsquid.buckpal.account.application.port.out

import backendsquid.buckpal.common.AccountId

interface AccountLock {
    fun lockAccount(accountId: AccountId)
    fun releaseAccount(accountId: AccountId)
}
