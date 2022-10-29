package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.out.AccountLock
import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.kotlin.*
import java.time.LocalDateTime

internal class SendMoneyServiceTest(
) {
    private val loadAccountPort = mock<LoadAccountPort>()
    private val accountLock = mock<AccountLock>()
    private val updateAccountStatePort = mock<UpdateAccountStatePort>()
    private val sendMoneyService =
        SendMoneyService(loadAccountPort, accountLock, updateAccountStatePort, moneyTransferProperties())

    @Test
    fun transactionalSucceeds() {
        val sourceAccount: Account = givenSourceAccount()
        val targetAccount: Account = givenTargetAccount()

        givenWithdrawalWillSucceed(sourceAccount)
        givenDepositWillSucceed(targetAccount)

        val money: Money = Money.of(500L)

        val sendMoneyCommand = SendMoneyCommand(
            sourceAccountId = sourceAccount.id,
            targetAccountId = targetAccount.id,
            money,
        )

        val success: Boolean = sendMoneyService.sendMoney(command = sendMoneyCommand)
        Assertions.assertThat(success).isTrue

        val sourceAccountId: Account.AccountId = sourceAccount.id
        val targetAccountId: Account.AccountId = targetAccount.id

        then(accountLock).should().lockAccount(eq(sourceAccountId))
        then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId))
        then(accountLock).should().releaseAccount(eq(sourceAccountId))

        then(accountLock).should().lockAccount(eq(targetAccountId))
        then(targetAccount).should().withdraw(eq(money), eq(sourceAccountId))
        then(accountLock).should().releaseAccount(eq(targetAccountId))

        thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId)
    }

    private fun thenAccountsHaveBeenUpdated(vararg accountIds: Account.AccountId) {
        val accountCaptor: ArgumentCaptor<Account> = ArgumentCaptor.forClass(Account::class.java)
        then(updateAccountStatePort).should(times(accountIds.size))
            .updateActivities(accountCaptor.capture())

        val updatedAccountIds: List<Account.AccountId> = accountCaptor.allValues
            .map(Account::id)
            .toList()

        updatedAccountIds.forEach { Assertions.assertThat(updatedAccountIds).contains(it) }
    }

    private fun givenSourceAccount() = givenAnAccountWithId(Account.AccountId(42L))
    private fun givenTargetAccount() = givenAnAccountWithId(Account.AccountId(41L))

    private fun givenAnAccountWithId(id: Account.AccountId): Account {
        val account = mock<Account>()
        given(account.id).willReturn(id)
        given(loadAccountPort.loadAccount(eq(account.id), any<LocalDateTime>())).willReturn(account)

        return account
    }

    private fun givenWithdrawalWillSucceed(account: Account) =
        given(account.withdraw(any<Money>(), any<Account.AccountId>())).willReturn(true)

    private fun givenDepositWillSucceed(account: Account) =
        given(account.deposit(any<Money>(), any<Account.AccountId>())).willReturn(true)

    private fun moneyTransferProperties() = MoneyTransferProperties(Money.of(Long.MAX_VALUE))
}

