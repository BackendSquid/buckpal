package backendsquid.buckpal.account.application.service

import backendsquid.buckpal.account.domain.Money

class ThresholdExceededException(threshold: Money, actual: Money) : RuntimeException(
    String.format("Maximum threshold for transferring money exceeded: tried to transfer $actual but threshold is $threshold!")
)
