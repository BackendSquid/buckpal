package backendsquid.buckpal.account.domain

import backendsquid.buckpal.common.AccountTestData.Companion.defaultAccount
import backendsquid.buckpal.common.ActivityTestData.Companion.defaultActivity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class AccountTest {
    @Test
    fun withdrawalSucceeds() {
        val accountId: Account.AccountId = Account.AccountId(1L)
        val account: Account = defaultAccount(
            withAccountId = accountId,
            withBaselineBalance = Money.of(555L),
            withActivityWindow = ActivityWindow(
                defaultActivity(
                    withTargetAccount = accountId,
                    withMoney = Money.of(999L)
                ),
                defaultActivity(
                    withTargetAccount = accountId,
                    withMoney = Money.of(1L)
                )

            )
        )


        val success: Boolean = account.withdraw(Money.of(555L), Account.AccountId(99L));

        Assertions.assertThat(success).isTrue
        Assertions.assertThat(account.activityWindow.getActivities()).hasSize(3)
        Assertions.assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L))
    }
}