package backendsquid.buckpal.account.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface ActivityRepository : JpaRepository<ActivityJpaEntity, Long> {
    @Query(
        """
        select a from ActivityJpaEntity a
        where a.ownerAccountId = :ownerAccountId
        and a.timestamp >= :since
    """
    )
    fun findByOwnerSince(
        @Param("ownerAccountId") ownerAccountId: Long,
        @Param("since") since: LocalDateTime
    ): List<ActivityJpaEntity>

    @Query(
        """
        select sum(a.amount) from ActivityJpaEntity a 
        where a.targetAccountId = :accountId 
        and a.ownerAccountId = :accountId 
        and a.timestamp < :until
    """
    )
    fun getDepositBalanceUntil(
        @Param("accountId") accountId: Long,
        @Param("until") until: LocalDateTime
    ): Long?

    @Query(
        """
        select sum(a.amount) from ActivityJpaEntity a 
        where a.sourceAccountId = :accountId 
        and a.ownerAccountId = :accountId 
        and a.timestamp < :until
    """
    )
    fun getWithdrawalBalanceUntil(
        @Param("accountId") accountId: Long,
        @Param("until") until: LocalDateTime
    ): Long?

}