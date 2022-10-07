package backendsquid.buckpal.account.domain

import java.math.BigInteger

class Money(private val amount: BigInteger) {
    companion object {
        val ZERO: Money = Money.of(0L)
        fun of(value: Long) = Money(BigInteger.valueOf(value))
        fun add(a : Money, b : Money) = Money(a.amount.add(b.amount))
    }
    fun plus(money : Money) = Money(this.amount.add(money.amount))
    fun negate() : Money = Money(this.amount.negate())
    fun isPositive(): Boolean = this.amount > BigInteger.ZERO
    fun isGreaterThan(money: Money): Boolean = this.amount.compareTo(money.amount) >= 1
}