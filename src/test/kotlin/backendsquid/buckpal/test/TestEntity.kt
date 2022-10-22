package backendsquid.buckpal.test

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class TestEntity(
    @Id
    var id: Long,
)
