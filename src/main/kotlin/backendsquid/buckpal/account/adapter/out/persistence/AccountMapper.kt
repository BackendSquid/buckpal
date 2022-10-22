package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Account.*
import backendsquid.buckpal.account.domain.Activity
import backendsquid.buckpal.account.domain.Activity.*
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {
    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long,
        depositBalance: Long,
    ): Account = account.id?.let { Account(
        id = AccountId(it),
        baselineBalance = Money.of(depositBalance) - Money.of(withdrawalBalance),
        activityWindow = mapToActivityWindow(activities),
    ) } ?: throw NullPointerException("account id is null")

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow =
        ActivityWindow(
            activities = activities.map { Activity(
                id = it.id?.let { activityId -> ActivityId(activityId) },
                ownerAccountId = AccountId(it.ownerAccountId),
                sourceAccountId = AccountId(it.sourceAccountId),
                targetAccountId = AccountId(it.targetAccountId),
                timestamp = it.timestamp,
                money = Money.of(it.amount),
            ) }.toMutableList()
        )

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity =
        ActivityJpaEntity(
            id = activity.id?.value,
            timestamp = activity.timestamp,
            ownerAccountId = activity.ownerAccountId.value,
            sourceAccountId = activity.sourceAccountId.value,
            targetAccountId = activity.targetAccountId.value,
            amount = activity.money.amount.toLong(),
        )

}
