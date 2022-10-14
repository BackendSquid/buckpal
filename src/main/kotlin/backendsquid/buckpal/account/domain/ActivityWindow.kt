package backendsquid.buckpal.account.domain

import backendsquid.buckpal.common.AccountId
import backendsquid.buckpal.common.Money
import java.time.LocalDateTime
import java.util.*

class ActivityWindow(
    private val activities: MutableList<Activity>,
) {
    val startTimeStamp: LocalDateTime
        get() = activities.minOfOrNull { it.timestamp } ?: throw IllegalStateException()

    val endTimeStamp: LocalDateTime
        get() = activities.maxOfOrNull { it.timestamp } ?: throw IllegalStateException()

    fun calculateBalance(accountId: AccountId): Money {
        val depositBalance = activities
            .filter { it.targetAccountId == accountId }
            .map { it.money }
            .reduce(Money::plus)

        val withdrawalBalance = activities
            .filter { it.sourceAccountId == accountId }
            .map { it.money }
            .reduce(Money::plus)

        return depositBalance - withdrawalBalance
    }

    constructor(vararg activities: Activity) : this(activities.toMutableList())

    fun getActivities(): List<Activity> = Collections.unmodifiableList(this.activities)

    fun addActivity(activity: Activity) {
        this.activities.add(activity)
    }
}
