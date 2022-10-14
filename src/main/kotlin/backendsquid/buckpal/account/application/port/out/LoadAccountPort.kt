package backendsquid.buckpal.account.application.port.out

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.common.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: AccountId, baseLineDate: LocalDateTime): Account
}
