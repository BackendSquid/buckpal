package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.common.SelfValidating

data class SendMoneyCommand(
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val money: Money,
): SelfValidating<SendMoneyCommand>() {

    init {
        validateSelf()
    }

}
