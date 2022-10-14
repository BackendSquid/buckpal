package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
): SendMoneyUseCase {
    override fun sendMoney(command: SendMoneyCommand): Boolean {
        val baseline = LocalDateTime.now().minusDays(10)
        val sourceAccount = loadAccountPort.loadAccount(
            accountId = command.sourceAccountId,
            baseLineDate = baseline,
        )
        val targetAccount = loadAccountPort.loadAccount(
            accountId = command.targetAccountId,
            baseLineDate = baseline,
        )

        accountLock.lockAccount(sourceAccount.id)
        if (!sourceAccount.withdraw(money = command.money, targetAccountId = targetAccount.id)) {
            accountLock.releaseAccount(sourceAccount.id)
            return false
        }

        accountLock.lockAccount(targetAccount.id)
        if (!targetAccount.deposit(money = command.money, sourceAccountId = sourceAccount.id)) {
            accountLock.releaseAccount(targetAccount.id)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccount.id)
        accountLock.releaseAccount(targetAccount.id)

        return true
    }
}
