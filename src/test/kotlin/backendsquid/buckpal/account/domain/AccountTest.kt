package backendsquid.buckpal.account.domain

import backendsquid.buckpal.account.domain.Account.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class AccountTest: BehaviorSpec({
    given("계좌 잔고가 555 + 999 + 1일 때") {
        val accountId = AccountId(1L)
        val account = Account(
            id = accountId,
            baselineBalance = Money.of(555L),
            activityWindow = ActivityWindow(
                Activity(
                    ownerAccountId = accountId,
                    sourceAccountId = AccountId(42L),
                    targetAccountId = accountId,
                    timestamp = LocalDateTime.now(),
                    money = Money.of(999L),
                ),
                Activity(
                    ownerAccountId = accountId,
                    sourceAccountId = AccountId(42L),
                    targetAccountId = accountId,
                    timestamp = LocalDateTime.now(),
                    money = Money.of(1L),
                )
            )
        )

        `when`("555를 인출하면") {
            val success = account.withdraw(
                money = Money.of(555L),
                targetAccountId = AccountId(99L),
            )
            then("성공해야 한다.") {
                success shouldBe true
            }
            then("새로운 활동이 추가된다.") {
                account.activityWindow.getActivities() shouldHaveSize 3
            }
            then("남은 돈은 1000이다.") {
                account.calculateBalance() shouldBe Money.of(1000L)
            }
        }
    }
})
