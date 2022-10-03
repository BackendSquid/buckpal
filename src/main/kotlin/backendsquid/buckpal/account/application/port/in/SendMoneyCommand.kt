package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.common.SelfValidating
import org.hibernate.validator.internal.engine.ConstraintViolationImpl
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

data class SendMoneyCommand(
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val money: Money,
): SelfValidating<SendMoneyCommand>() {

    init {
        if (!money.isPositive()) {
            throw IllegalArgumentException("Money must be positive")
        }

        validateSelf()
    }

}
