package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.account.domain.Account.AccountId
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.common.WebAdapter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
internal class SendMoneyController {
    private val sendMoneyUseCase: SendMoneyUseCase? = null

    @PostMapping(path = ["/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}"])
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long?,
        @PathVariable("targetAccountId") targetAccountId: Long?,
        @PathVariable("amount") amount: Long?
    ) {
        val command = SendMoneyCommand(
            AccountId(sourceAccountId!!),
            AccountId(targetAccountId!!),
            Money.of(amount!!)
        )
        sendMoneyUseCase!!.sendMoney(command)
    }
}
