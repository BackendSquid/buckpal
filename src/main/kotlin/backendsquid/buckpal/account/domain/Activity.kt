package backendsquid.buckpal.account.domain

import org.springframework.lang.NonNull
import java.time.LocalDateTime

class Activity(
    val id : ActivityId? = null,
    @field:NonNull val ownerAccountId : Account.AccountId,
    @field:NonNull val sourceAccountId : Account.AccountId,
    @field:NonNull val targetAccountId : Account.AccountId,
    @field:NonNull val timestamp : LocalDateTime,
    @field:NonNull val money : Money
) {
    class ActivityId(private val value: Long)
}