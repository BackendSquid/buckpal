package backendsquid.buckpal.account.adapter.out.lock

import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.domain.Account.*
import org.springframework.stereotype.Component

@Component
class AccountLockAdapter: AccountLock {

    override fun lockAccount(accountId: AccountId) {
        //TODO("Not yet implemented")
    }

    override fun releaseAccount(accountId: AccountId) {
        //TODO("Not yet implemented")
    }
}
