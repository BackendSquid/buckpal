package backendsquid.buckpal

import backendsquid.buckpal.account.application.port.out.LoadAccountPort
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SendMoneySystemTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired private val loadAccountPort: LoadAccountPort,
): BehaviorSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    init {
        given("1번에서 2번 계좌로 돈 전송") {
            `when`("60을 보내면") {
                val response = restTemplate.exchange(
                    "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                    HttpMethod.POST,
                    HttpEntity(null, HttpHeaders().apply { add("Content-Type", "application/json") }),
                    Any::class.java,
                    1L,
                    2L,
                    60,
                )
                then("요청에 성공한다") {
                    response.statusCode shouldBe HttpStatus.OK
                }
                then("1번은 돈이 40이 된다.") {
                    val sourceAccount = loadAccountPort.loadAccount(Account.AccountId(1L), LocalDateTime.now())
                    sourceAccount.calculateBalance() shouldBe Money.of(40)
                }
                then("2번은 돈이 60이 된다.") {
                    val targetAccount = loadAccountPort.loadAccount(Account.AccountId(2L), LocalDateTime.now())
                    targetAccount.calculateBalance() shouldBe Money.of(60)
                }
            }
        }
    }
}
