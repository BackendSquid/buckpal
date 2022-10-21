package backendsquid.buckpal.account.domain

import java.math.BigInteger

data class Money(
    val amount: BigInteger
): Comparable<Money> {
    companion object {
        val ZERO: Money = Money.of(value = 0)
        fun of(value: Long): Money = Money(BigInteger.valueOf(value))
        fun add(a: Money, b: Money): Money = Money(a.amount + b.amount)
        fun subtract(a: Money, b: Money): Money = Money(a.amount - b.amount)
    }

    fun isPositiveOrZero(): Boolean = this.amount >= BigInteger.ZERO
    fun isNegative(): Boolean = this.amount < BigInteger.ZERO
    fun isPositive(): Boolean = this.amount > BigInteger.ZERO

    override fun compareTo(other: Money): Int = this.amount.compareTo(other.amount)
    operator fun plus(other: Money): Money = Money(this.amount + other.amount)
    operator fun minus(other: Money): Money = Money(this.amount - other.amount)
    operator fun unaryMinus(): Money = Money(-this.amount)
}
