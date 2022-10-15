package backendsquid.buckpal.account.adapter.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import org.springframework.stereotype.Service

@Service
class DoNothingSendMoneyUseCase: SendMoneyUseCase {
    override fun sendMoney(command: SendMoneyCommand): Boolean = true
}
