package backendsquid.buckpal.account.domain

class ActivityWindow(
    private val activities : MutableList<Activity>
) {
    fun calculateBalance(accountId: Account.AccountId): Money {
        val depositBalance: Money = activities.filter { it.targetAccountId == accountId }.map { it.money }.reduce(Money::plus)
        val withdrawalBalance: Money = activities.filter { it.sourceAccountId == accountId }.map { it.money }.reduce(Money::plus)
        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
}