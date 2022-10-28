package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

open class SendMoneyServiceTest {

    var loadAccountPort = mockk<LoadAccountPort>(relaxed = true)
    var accountLock = mockk<AccountLock>(relaxed = true)
    var updateAccountStatePort = mockk<UpdateAccountStatePort>(relaxed = true)
    val sendMoneyService: SendMoneyService = SendMoneyService(
        loadAccountPort = loadAccountPort,
        accountLock = accountLock,
        updateAccountStatePort = updateAccountStatePort,
    )

    @Test
    fun `송금 트랜잭션이 성공한다`() {
        val sourceAccount: Account = givenSourceAccount()
        val targetAccount: Account = givenTargetAccount()

        givenWithdrawalWillSucceed(sourceAccount)
        givenDepositWillSucceed(targetAccount)


        val money = Money.of(500L)

        val command = SendMoneyCommand(
            sourceAccountId = sourceAccount.id,
            targetAccountId = targetAccount.id,
            money = money
        )

        val success: Boolean = sendMoneyService.sendMoney(command)
        assertThat(success).isTrue

        verify {
            val sourceAccountId: Account.AccountId = sourceAccount.id
            val targetAccountId: Account.AccountId = targetAccount.id

            accountLock.lockAccount(eq(sourceAccountId))
            sourceAccount.withdraw(eq(money), eq(targetAccountId))
            accountLock.releaseAccount(eq(sourceAccountId))

            accountLock.lockAccount(eq(targetAccountId))
            targetAccount.deposit(eq(money), eq(sourceAccountId))
            accountLock.releaseAccount(eq(targetAccountId))
        }
    }

    private fun thenAccountsHaveBeenUpdated(vararg accountIds: Account.AccountId) {
        val accountCaptor = slot<Account>()
        val updatedAccountIds = mutableListOf<Account.AccountId>()

        verify(exactly = accountIds.size) {
            updateAccountStatePort.updateActivities(
                capture(accountCaptor)
            )
        }
        updatedAccountIds.add(accountCaptor.captured.id)

        for (accountId in accountIds) {
            assertThat(updatedAccountIds).contains(accountId)
        }
    }

    private fun givenSourceAccount(): Account = givenAnAccountWithId(Account.AccountId(41L))
    private fun givenTargetAccount(): Account = givenAnAccountWithId(Account.AccountId(42L))

    private fun givenAnAccountWithId(id: Account.AccountId): Account {
        val account = mockk<Account>()

        every {
            account.id
        } returns id

        every {
            loadAccountPort.loadAccount(
                accountId = account.id,
                baseLineDate = any()
            )
        } returns account

        return account
    }

    private fun givenDepositWillFail(account: Account) {
        every {
            account.deposit(
                money = any(),
                sourceAccountId = any()
            )
        } returns false
    }

    private fun givenDepositWillSucceed(account: Account) {
        every {
            account.deposit(
                money = any(),
                sourceAccountId = any()
            )
        } returns true
    }

    private fun givenWithdrawalWillSucceed(account: Account) {
        every {
            account.withdraw(
                money = any(),
                targetAccountId = any()
            )
        } returns true
    }

    private fun givenWithdrawalWillFail(account: Account) {
        every {
            account.withdraw(
                money = any(),
                targetAccountId = any()
            )
        } returns false
    }
}