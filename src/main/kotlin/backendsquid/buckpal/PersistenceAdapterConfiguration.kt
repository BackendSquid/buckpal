package backendsquid.buckpal

import backendsquid.buckpal.account.adapter.out.persistence.AccountMapper
import backendsquid.buckpal.account.adapter.out.persistence.AccountPersistenceAdapter
import backendsquid.buckpal.account.adapter.out.persistence.AccountRepository
import backendsquid.buckpal.account.adapter.out.persistence.ActivityRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories
class PersistenceAdapterConfiguration {

    @Bean
    fun accountPersistenceAdapter(
        accountRepository: AccountRepository,
        activityRepository: ActivityRepository,
        accountMapper: AccountMapper
    ): AccountPersistenceAdapter = AccountPersistenceAdapter(
        accountRepository = accountRepository,
        activityRepository = activityRepository,
        accountMapper = accountMapper
    )

    @Bean
    fun accountMapper() = AccountMapper()
}