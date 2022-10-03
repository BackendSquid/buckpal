package backendsquid.buckpal.account.application.port.out

import backendsquid.buckpal.account.domain.Account
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: Account.AccountId, baseLineDate: LocalDateTime): Account
}
