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
            Account.AccountId(account.id!!),
            baselineBalance,
            mapToActivityWindow(activities)
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow {
        val mappedActivities: MutableList<Activity> = ArrayList()
        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    Activity.ActivityId(activity.id!!),
                    Account.AccountId(activity.ownerAccountId),
                    Account.AccountId(activity.sourceAccountId),
                    Account.AccountId(activity.targetAccountId),
                    activity.timestamp,
                    Money.of(activity.amount)
                )
            )
        }
        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity {
        return ActivityJpaEntity(
            activity.id?.value,
            activity.timestamp,
            activity.ownerAccountId.value,
            activity.sourceAccountId.value,
            activity.targetAccountId.value,
            activity.money.amount.longValueExact()
        )
    }
}