package backendsquid.buckpal.account.domain

import java.time.LocalDateTime

class Account(
    val id: AccountId,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow,
) {

    fun calculateBalance(): Money =
        this.baselineBalance + activityWindow.calculateBalance(id)

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (!mayWithdraw(money)) {
            return false
        }

        val withdrawal = Activity(
            ownerAccountId = this.id,
            sourceAccountId = this.id,
            targetAccountId = targetAccountId,
            timestamp = LocalDateTime.now(),
            money = money
        )

        this.activityWindow.addActivity(withdrawal)
        return true
    }

    private fun mayWithdraw(money: Money): Boolean = Money
        .add(
            this.calculateBalance(),
            -money,
        ).isPositiveOrZero()

    data class AccountId(
        private val value: Long,
    )
}