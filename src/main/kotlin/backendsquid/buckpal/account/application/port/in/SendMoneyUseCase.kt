package backendsquid.buckpal.account.application.port.`in`

import backendsquid.buckpal.account.application.service.SendMoneyResult

interface SendMoneyUseCase {
    fun sendMoney(command: SendMoneyCommand): SendMoneyResult
}
