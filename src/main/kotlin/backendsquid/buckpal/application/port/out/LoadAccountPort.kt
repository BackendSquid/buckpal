package backendsquid.buckpal.application.port.out

import backendsquid.buckpal.account.domain.Account
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: Account.AccountId, baselineDate: LocalDateTime): Account
}