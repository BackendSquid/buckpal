package backendsquid.buckpal.account.adapter.`in`.web

import javax.validation.constraints.Positive

data class SendMoneyResource(
    @field:Positive
    val sourceAccountId: Long,
    @field:Positive
    val targetAccountId: Long,
    @field:Positive
    val amount: Long,
)
