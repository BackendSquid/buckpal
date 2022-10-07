package backendsquid.buckpal.application.service

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.application.port.out.AccountLock
import backendsquid.buckpal.application.port.out.LoadAccountPort
import backendsquid.buckpal.application.port.out.UpdateAccountStatePort
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
) : SendMoneyUseCase {
    override fun sendMoney(command: SendMoneyCommand): Boolean {
        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount: Account = loadAccountPort.loadAccount(
            command.sourceAccountId,
            baselineDate
        )

        val targetAccount: Account = loadAccountPort.loadAccount(
            command.targetAccountId,
            baselineDate
        )

        val sourceAccountId: Account.AccountId = sourceAccount.id ?: throw IllegalStateException("expected source account ID not to be empty")
        val targetAccountId: Account.AccountId = targetAccount.id ?: throw IllegalStateException("expected source account ID not to be empty")

        accountLock.lockAccount(sourceAccountId)
        if(!sourceAccount.withdraw(command.money, targetAccountId)){
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount((targetAccountId))
        if(!sourceAccount.deposit(command.money, sourceAccountId)){
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)
        return true
    }
}