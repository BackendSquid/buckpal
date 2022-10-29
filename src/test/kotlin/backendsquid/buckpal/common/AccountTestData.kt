package backendsquid.buckpal.common

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money

class AccountTestData {
    companion object {
        fun defaultAccount(
            withAccountId: Account.AccountId? = null,
            withBaselineBalance: Money?,
            withActivityWindow: ActivityWindow
        ) = Account(
            id = withAccountId ?: Account.AccountId(42L),
            baselineBalance = withBaselineBalance ?: Money.of(999L),
            activityWindow = withActivityWindow
        )
    }
}