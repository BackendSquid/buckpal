package backendsquid.buckpal.account.adapter.out.persistence

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activity")
class ActivityJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column var timestamp: LocalDateTime,
    @Column var ownerAccountId: Long,
    @Column var sourceAccountId: Long,
    @Column var targetAccountId: Long,
    @Column var amount: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActivityJpaEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
