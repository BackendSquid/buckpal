package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money
import backendsquid.buckpal.common.AccountTestData.Companion.defaultAccount
import backendsquid.buckpal.common.ActivityTestData.Companion.defaultActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@DataJpaTest
@Import(value = [AccountPersistenceAdapter::class, AccountMapper::class])
internal class AccountPersistenceAdapterTest {
    @Autowired
    lateinit var adapterUnderTest: AccountPersistenceAdapter

    @Autowired
    lateinit var activityRepository: ActivityRepository

    @Test
    @Sql("AccountPersistenceAdapterTest.sql")
    fun loadsAccount() {
        val account: Account = adapterUnderTest.loadAccount(
            accountId = Account.AccountId(1L),
            baseLineDate = LocalDateTime.of(2018, 8, 10, 0, 0)
        )

        assertThat(account.activityWindow.getActivities()).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(500))
    }

    @Test
    fun updatesActivities() {
        val account: Account = defaultAccount(
            withBaselineBalance = Money.of(555L),
            withActivityWindow = ActivityWindow(
                defaultActivity(
                    withTargetAccount = null,
                    withMoney = Money.of(1L)
                )
            )
        )
        adapterUnderTest.updateActivities(account)
        assertThat(activityRepository.count()).isEqualTo(1)
        val savedActivity = activityRepository.findAll()[0]
        assertThat(savedActivity.amount).isEqualTo(1L)
    }

}