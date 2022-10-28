package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import backendsquid.buckpal.account.domain.Account
import backendsquid.buckpal.account.domain.Money
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [SendMoneyController::class])
internal class SendMoneyControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    // Cannot access class 'org.mockito.Answers'. Check your module classpath for missing or conflicting dependencies
    @MockBean
    lateinit var sendMoneyUseCase: SendMoneyUseCase

    @Test
    fun `송금 테스트`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(
                "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                41L, 42L, 500
            )
                .header("Content-Type", "application/json")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify {
            sendMoneyUseCase.sendMoney(
                eq(
                    SendMoneyCommand(
                        sourceAccountId = Account.AccountId(41L),
                        targetAccountId = Account.AccountId(42L),
                        money = Money.of(500L)
                    )
                )
            )
        }
    }

}