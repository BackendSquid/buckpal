package backendsquid.buckpal.common

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money

class AccountTestData {
    companion object {
        fun defaultAccount(
            withAccountId: Account.AccountId,
            withBaselineBalance: Money,
            withActivityWindow: ActivityWindow
        ) = Account(
            id = withAccountId,
            baselineBalance = withBaselineBalance,
            activityWindow = withActivityWindow
        )
    }
}