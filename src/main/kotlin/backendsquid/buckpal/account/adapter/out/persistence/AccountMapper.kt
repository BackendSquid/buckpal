package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Activity
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {
    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long,
        depositBalance: Long
    ): Account? {
        val baselineBalance: Money = Money.subtract(
            Money.of(depositBalance),
            Money.of(withdrawalBalance)
        )
        return Account.withId(
            accountId = Account.AccountId(account.id!!),
            baselineBalance = baselineBalance,
            activityWindow = mapToActivityWindow(activities)
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow {
        val mappedActivities: MutableList<Activity> = ArrayList()
        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    id = Activity.ActivityId(activity.id!!),
                    ownerAccountId = Account.AccountId(activity.ownerAccountId),
                    sourceAccountId = Account.AccountId(activity.sourceAccountId),
                    targetAccountId = Account.AccountId(activity.targetAccountId),
                    timestamp = activity.timestamp,
                    money = Money.of(activity.amount)
                )
            )
        }
        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity =
        ActivityJpaEntity(
            id = activity.id?.value,
            timestamp = activity.timestamp,
            ownerAccountId = activity.ownerAccountId.value,
            sourceAccountId = activity.sourceAccountId.value,
            targetAccountId = activity.targetAccountId.value,
            amount = activity.money.amount.longValueExact()
        )

}