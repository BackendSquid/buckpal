package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Account.*
import backendsquid.buckpal.account.domain.Money
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SendMoneyServiceTest: BehaviorSpec({
    given("41 계좌에서 42 계좌로 돈을 보내는 것은 성공한다.") {
        val loadAccountPort = mockk<LoadAccountPort>()
        val accountLock = mockk<AccountLock> {
            every { lockAccount(any()) } returns Unit
            every { releaseAccount(any()) } returns Unit
        }

        val updateAccountStatePort = mockk<UpdateAccountStatePort> {
            every { updateActivities(any()) } returns Unit
        }

        val sendMoneyService = SendMoneyService(loadAccountPort, accountLock, updateAccountStatePort)

        fun accountWithId(givenId: AccountId): Account = mockk {
            every { id } returns givenId
            every { loadAccountPort.loadAccount(givenId, any()) } returns this
        }

        val sourceAccount = accountWithId(AccountId(41))
        val targetAccount = accountWithId(AccountId(42))

        every { sourceAccount.withdraw(any(), any()) } returns true
        every { targetAccount.deposit(any(), any()) } returns true

        `when`("이때 500을 전송하면") {
            val money = Money.of(500L)
            val command = SendMoneyCommand(
                sourceAccountId = sourceAccount.id,
                targetAccountId = targetAccount.id,
                money = money,
            )

            val success = sendMoneyService.sendMoney(command)

            then("성공해야 한다.") {
                success shouldBe true

                val sourceAccountId = sourceAccount.id
                val targetAccountId = targetAccount.id
                verify {
                    loadAccountPort.loadAccount(sourceAccountId, any())
                    loadAccountPort.loadAccount(targetAccountId, any())

                    accountLock.lockAccount(sourceAccountId)
                    sourceAccount.withdraw(money, targetAccountId)
                    accountLock.releaseAccount(targetAccountId)

                    accountLock.lockAccount(targetAccountId)
                    targetAccount.deposit(money, sourceAccountId)
                    accountLock.releaseAccount(targetAccountId)

                    updateAccountStatePort.updateActivities(sourceAccount)
                    updateAccountStatePort.updateActivities(targetAccount)
                }
            }
        }
    }
})
