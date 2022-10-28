package backendsquid.buckpal.common

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Activity
import backendsquid.buckpal.account.domain.Money
import java.time.LocalDateTime

class ActivityTestData {
    companion object {
        fun defaultActivity(
            withTargetAccount: Account.AccountId,
            withMoney: Money
        ) = Activity(
            ownerAccountId = Account.AccountId(42L),
            sourceAccountId = Account.AccountId(42L),
            timestamp = LocalDateTime.now(),
            targetAccountId = withTargetAccount,
            money = withMoney
        )
    }
}