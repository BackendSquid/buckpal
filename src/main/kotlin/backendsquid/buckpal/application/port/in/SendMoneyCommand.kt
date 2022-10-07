package backendsquid.buckpal.application.port.`in`

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.common.SelfValidating
import javax.validation.constraints.NotNull

data class SendMoneyCommand(
    @field:NotNull val sourceAccountId: Account.AccountId,
    @field:NotNull val targetAccountId: Account.AccountId,
    @field:NotNull val money: Money
) : SelfValidating<SendMoneyCommand>() {
    init {
        this.validateSelf()
    }
}