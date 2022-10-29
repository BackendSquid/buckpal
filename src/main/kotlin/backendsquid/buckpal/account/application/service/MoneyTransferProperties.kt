package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.domain.Money
import org.springframework.stereotype.Component

@Component
class MoneyTransferProperties(
    val maximumTransferThreshold: Money = Money.of(1_000_000L)
)