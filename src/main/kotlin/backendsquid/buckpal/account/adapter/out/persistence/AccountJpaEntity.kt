package backendsquid.buckpal.account.adapter.out.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
class AccountJpaEntity(
    @Id
    @GeneratedValue
    var id: Long?
)