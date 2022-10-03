package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SendMoneyCommandTest {
    @Test
    fun validationTest() {
        assertThatThrownBy {
            SendMoneyCommand(
                sourceAccountId = Account.AccountId(1),
                targetAccountId = Account.AccountId(2),
                money = Money.of(-10),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}