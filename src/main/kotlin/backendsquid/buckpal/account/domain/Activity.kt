package backendsquid.buckpal.account.domain

import backendsquid.buckpal.common.AccountId
import backendsquid.buckpal.common.Money
import java.time.LocalDateTime

data class Activity(
    val id: ActivityId? = null,
    val ownerAccountId: AccountId,
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    val timestamp: LocalDateTime,
    val money: Money,
) {
    data class ActivityId(
        private val value: Long,
    )
}
