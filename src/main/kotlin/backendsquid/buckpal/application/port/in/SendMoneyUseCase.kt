package backendsquid.buckpal.application.port.`in`

interface SendMoneyUseCase {
    fun sendMoney(command: SendMoneyCommand): Boolean
}