package backendsquid.buckpal.account.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId

internal class AccountTest {

    @Test
    fun withdrawSucceeds() {
        val accountId: Account.AccountId = Account.AccountId(1L)
        val account: Account = defaultAccount(
            accountId = accountId,
            baseLineBalance = 555L,
            activityWindow = ActivityWindow(
                mutableListOf(
                    defaultActivity(
                        targetAccountId = accountId,
                        money = 999L
                    ),
                    defaultActivity(
                        targetAccountId = accountId,
                        money = 1L
                    )
                )
            )
        )

        val success: Boolean = account.withdraw(
            money = Money.of(555L),
            targetAccountId = Account.AccountId(99L)
        )

        Assertions.assertThat(success).isTrue
        Assertions.assertThat(account.activityWindow.getActivities()).hasSize(3)
        Assertions.assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L))
    }
}


private fun defaultAccount(
    accountId: Account.AccountId = Account.AccountId(42L),
    baseLineBalance: Long = 999L,
    activityWindow: ActivityWindow = ActivityWindow(mutableListOf(defaultActivity(), defaultActivity()))
) = Account(
    id = accountId,
    baselineBalance = Money.of(baseLineBalance),
    activityWindow = activityWindow
)

private fun defaultActivity(
    ownerAccountId: Account.AccountId = Account.AccountId(42L),
    sourceAccountId: Account.AccountId = Account.AccountId(42L),
    targetAccountId: Account.AccountId = Account.AccountId(41L),
    money: Long = 999L,
) = Activity(
    ownerAccountId = ownerAccountId,
    sourceAccountId = sourceAccountId,
    targetAccountId = targetAccountId,
    timestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
    money = Money.of(money)
)