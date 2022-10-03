package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import javax.transaction.Transactional

@Transactional
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
): SendMoneyUseCase {
    override fun sendMoney(command: SendMoneyCommand) {
        TODO("Not yet implemented")
    }
}
