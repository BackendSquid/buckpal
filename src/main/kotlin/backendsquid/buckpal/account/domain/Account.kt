package backendsquid.buckpal.account.domain

import java.time.LocalDateTime

class Account(
    val id: AccountId?,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow
) {
    fun calculateBalance(): Money = Money.add(
        this.baselineBalance,
        this.activityWindow.calculateBalance(this.id!!)
    )

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (!mayWithdraw(money)) {
            return false;
        }

        val withdrawal: Activity = Activity(
            ownerAccountId = this.id!!,
            sourceAccountId = this.id,
            targetAccountId = targetAccountId,
            timestamp = LocalDateTime.now(),
            money = money
        )
        return true;
    }

    private fun mayWithdraw(money: Money): Boolean = Money.add(
        a = this.calculateBalance(),
        b = money.negate()
    ).isPositive()

    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit: Activity = Activity(
            ownerAccountId = this.id!!,
            sourceAccountId = sourceAccountId,
            targetAccountId = this.id,
            timestamp = LocalDateTime.now(),
            money = money
        )
        return true
    }

    class AccountId(private val value: Long)
}