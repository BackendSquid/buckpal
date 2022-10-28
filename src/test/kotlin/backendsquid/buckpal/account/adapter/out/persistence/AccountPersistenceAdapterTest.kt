package backendsquid.buckpal.account.adapter.out.persistence

import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Account.*
import backendsquid.buckpal.account.domain.Activity
import backendsquid.buckpal.account.domain.ActivityWindow
import backendsquid.buckpal.account.domain.Money
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@DataJpaTest
@Import(AccountPersistenceAdapter::class, AccountMapper::class)
internal class AccountPersistenceAdapterTest(
    @Autowired private val adapter: AccountPersistenceAdapter,
    @Autowired private val activityRepository: ActivityRepository,
): FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    init {
        test("AccountPersistenceAdapterTest.sql") {
            val account = adapter.loadAccount(
                AccountId(1L),
                LocalDateTime.of(2022, 10, 5, 0, 0)
            )

            account.activityWindow.getActivities() shouldHaveSize 0
            account.calculateBalance() shouldBe Money.of(100)
        }

        test("updateActivities") {
            val account = Account(
                id = AccountId(5),
                baselineBalance = Money.of(555),
                activityWindow = ActivityWindow(
                    Activity(
                        id = null,
                        ownerAccountId = AccountId(5),
                        sourceAccountId = AccountId(5),
                        targetAccountId = AccountId(1),
                        timestamp = LocalDateTime.now(),
                        money = Money.of(1L)
                    )
                )
            )

            adapter.updateActivities(account)

            activityRepository.count() shouldBe 2
            val savedActivity = activityRepository.findAll().last()
            savedActivity.amount shouldBe 1L
        }
    }

}
