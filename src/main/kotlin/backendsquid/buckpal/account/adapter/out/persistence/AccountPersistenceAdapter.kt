package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.application.port.out.UpdateAccountStatePort
import backendsquid.buckpal.account.domain.Account
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Component
class AccountPersistenceAdapter(
    private val accountRepository: SpringDataAccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper
) : LoadAccountPort, UpdateAccountStatePort {
    override fun loadAccount(
        accountId: Account.AccountId,
        baseLineDate: LocalDateTime
    ): Account {

        val account = accountRepository.findByIdOrNull(accountId.value) ?: throw EntityNotFoundException()

        val activities = activityRepository.findByOwnerSince(
            ownerAccountId = accountId.value,
            since = baseLineDate
        )

        val withdrawalBalance = orZero(
            activityRepository
                .getWithdrawalBalanceUntil(
                    accountId = accountId.value,
                    until = baseLineDate
                )
        )

        val depositBalance = orZero(
            activityRepository
                .getDepositBalanceUntil(
                    accountId = accountId.value,
                    until = baseLineDate
                )
        )

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )!!

    }

    override fun updateActivities(account: Account) {
        val activities = account.activityWindow.getActivities()
        activities.forEach { it.id ?: activityRepository.save(accountMapper.mapToJpaEntity(it)) }
    }

    fun orZero(value: Long?): Long = value ?: 0L;
}