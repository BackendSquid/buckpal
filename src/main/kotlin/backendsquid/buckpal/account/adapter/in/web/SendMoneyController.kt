package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase
) {
    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable("amount") amount: Long
    ) : Boolean {
        val command = SendMoneyCommand(
            sourceAccountId = Account.AccountId(sourceAccountId),
            targetAccountId = Account.AccountId(targetAccountId),
            money = Money.of(amount),
        )
        return sendMoneyUseCase.sendMoney(command)
    }
}
