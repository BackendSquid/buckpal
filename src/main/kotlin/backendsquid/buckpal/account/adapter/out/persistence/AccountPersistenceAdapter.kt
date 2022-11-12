package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Account.AccountId
import backendsquid.buckpal.common.PersistenceAdapter
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@PersistenceAdapter
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper,
): LoadAccountPort, UpdateAccountStatePort {

    override fun loadAccount(accountId: AccountId, baseLineDate: LocalDateTime): Account {
        val account = accountRepository.findByIdOrNull(id = accountId.value) ?: throw EntityNotFoundException()
        val activities = activityRepository.findByOwnerSince(ownerAccountId = accountId.value, since = baseLineDate)

        val withdrawalBalance = activityRepository
            .getWithdrawalBalanceUntil(accountId = accountId.value, until = baseLineDate)
            ?: 0L

        val depositBalance = activityRepository
            .getDepositBalanceUtil(accountId = accountId.value, until = baseLineDate)
            ?: 0L

        return account.mapToDomainEntity(
            activities = activities,
            withdrawalBalance = withdrawalBalance,
            depositBalance = depositBalance,
        )
    }

    override fun updateActivities(account: Account) = account
        .activityWindow
        .getActivities()
        .filter { it.id == null }
        .map { it.mapToJpaEntity() }
        .let {
            activityRepository.saveAll(it)
            Unit
        }

}
