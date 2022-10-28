package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@Validated
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase,
) {

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") @Positive sourceAccountId: Long,
        @PathVariable("targetAccountId") @Positive targetAccountId: Long,
        @PathVariable("amount") @Positive amount: Long,
    ): Boolean {
        val command = SendMoneyCommand(
            sourceAccountId = sourceAccountId,
            targetAccountId = targetAccountId,
            money = amount,
        )

        return sendMoneyUseCase.sendMoney(command = command)
    }

    @PostMapping("/accounts/send")
    fun sendMoneyByBody(
        @RequestBody @Valid sendMoneyResource: SendMoneyResource
    ): Boolean {
        val command = SendMoneyCommand(
            sourceAccountId = sendMoneyResource.sourceAccountId,
            targetAccountId = sendMoneyResource.targetAccountId,
            money = sendMoneyResource.amount,
        )

        return sendMoneyUseCase.sendMoney(command = command)
    }
}
