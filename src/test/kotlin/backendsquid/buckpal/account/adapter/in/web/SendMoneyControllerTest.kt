package backendsquid.buckpal.account.adapter.`in`.web

import backendsquid.buckpal.account.application.port.`in`.SendMoneyCommand
import backendsquid.buckpal.account.application.port.`in`.SendMoneyUseCase
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SendMoneyController::class])
internal class SendMoneyControllerTest(
    @Autowired private val mockMvc: MockMvc
): FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @MockkBean(relaxed = true)
    private lateinit var sendMoneyUseCase: SendMoneyUseCase

    init {
        test("41에서 42로 500을 입금한다.") {
            mockMvc
                .perform(
                    post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                        41L, 42L, 500)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk)

            verify { sendMoneyUseCase.sendMoney(
                SendMoneyCommand(
                    sourceAccountId = 41L,
                    targetAccountId = 42L,
                    money = 500,
                )
            ) }
        }
    }

}
