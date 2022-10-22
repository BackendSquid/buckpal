package backendsquid.buckpal.test

import org.springframework.data.jpa.repository.JpaRepository

interface TestEntityRepository: JpaRepository<TestEntity, Long>
