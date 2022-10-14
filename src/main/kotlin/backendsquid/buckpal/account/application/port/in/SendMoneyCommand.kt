package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.common.AccountId
import backendsquid.buckpal.common.Money
import backendsquid.buckpal.common.SelfValidating

data class SendMoneyCommand(
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    val money: Money,
): SelfValidating<SendMoneyCommand>() {

    init {
        if (!money.isPositive()) {
            throw IllegalArgumentException("Money must be positive")
        }

        validateSelf()
    }

}
