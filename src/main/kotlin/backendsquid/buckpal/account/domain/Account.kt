package backendsquid.buckpal.account.domain

import java.time.LocalDateTime

class Account(
    val id: AccountId? = null,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow,
) {

    fun calculateBalance(): Money = if (id == null) {
        this.baselineBalance
    } else {
        this.baselineBalance + activityWindow.calculateBalance(id)
    }

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (this.id == null || !mayWithdraw(money)) {
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