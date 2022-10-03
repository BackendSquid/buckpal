package backendsquid.buckpal.account.domain

import java.time.LocalDateTime

data class Activity(
    val id: ActivityId? = null,
    val ownerAccountId: Account.AccountId,
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val timestamp: LocalDateTime,
    val money: Money,
) {
    data class ActivityId(
        private val value: Long,
    )
}
