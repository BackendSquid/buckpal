package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Account.*
import backendsquid.buckpal.account.domain.Activity
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money

fun AccountJpaEntity.mapToDomainEntity(
    activities: List<ActivityJpaEntity>,
    withdrawalBalance: Long,
    depositBalance: Long,
): Account = id?.let {
    Account(
        id = AccountId(it),
        baselineBalance = Money.of(depositBalance) - Money.of(withdrawalBalance),
        activityWindow = activities.mapToActivityWindow(),
    )
} ?: throw NullPointerException("account id is null")

fun List<ActivityJpaEntity>.mapToActivityWindow(): ActivityWindow =
    ActivityWindow(
        activities = map { Activity(
            id = it.id?.let { activityId -> Activity.ActivityId(activityId) },
            ownerAccountId = AccountId(it.ownerAccountId),
            sourceAccountId = AccountId(it.sourceAccountId),
            targetAccountId = AccountId(it.targetAccountId),
            timestamp = it.timestamp,
            money = Money.of(it.amount),
        ) }.toMutableList()
    )

fun Activity.mapToJpaEntity(): ActivityJpaEntity =
    ActivityJpaEntity(
        id = id?.value,
        timestamp = timestamp,
        ownerAccountId = ownerAccountId.value,
        sourceAccountId = sourceAccountId.value,
        targetAccountId = targetAccountId.value,
        amount = money.amount.toLong(),
    )
