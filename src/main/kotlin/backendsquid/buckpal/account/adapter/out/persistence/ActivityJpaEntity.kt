package backendsquid.buckpal.account.adapter.out.persistence

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "activity")
class ActivityJpaEntity(
    @Id
    @GeneratedValue
    var id: Long?,
    @Column
    var timestamp: LocalDateTime,
    @Column
    var ownerAccountId: Long,
    @Column
    var sourceAccountId: Long,
    @Column
    var targetAccountId: Long,
    @Column
    var amount: Long
)