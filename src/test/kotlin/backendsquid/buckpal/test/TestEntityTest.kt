package backendsquid.buckpal.test

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityExistsException
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@DataJpaTest
class TestEntityTest(
    @Autowired private val repository: TestEntityRepository,
) {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Test
    @DisplayName("id가 0이 아닌 경우, save는 persist로 실행")
    fun testDataJpaSavePersist() {
        val newEntity = TestEntity(id = 0)

        val saved = repository.save(newEntity)

        em.flush()
        println(saved.id)
        assertThat(saved.id).isEqualTo(0)
    }

    @Test
    @DisplayName("id가 0이 아닌 경우, id 생성 전략이 없으면 persist 정상 실행")
    fun testWithEntityManagerPersistNew() {
        val newEntity = TestEntity(id = 0)
        em.persist(newEntity)

        println(newEntity.id)
        assertThat(newEntity.id).isNotEqualTo(0)
    }

    @Test
    @DisplayName("id가 0이 아닌 경우, select 후 insert 실행")
    fun testWithEntityManagerPersistDuplicate() {
        val newEntity = TestEntity(id = 10)
        em.persist(newEntity)
        em.flush()

        assertThat(newEntity.id).isEqualTo(10)
        assertThatThrownBy {
            val newEntity2 = TestEntity(id = 10)
            em.persist(newEntity2)
        }.isInstanceOf(EntityExistsException::class.java)
    }

    @Test
    @DisplayName("id가 0이 아닌 경우, select 후 insert 실행")
    fun testDataJpaSaveMerge() {
        val newEntity = TestEntity(id = 3_000_000)

        val saved = repository.save(newEntity)
        em.flush()

        assertThat(saved.id).isEqualTo(3_000_000)
    }

    @Test
    @DisplayName("merge는 select 후 insert 실행, id 생성 전략이 없는 경우")
    fun testWithEntityManagerUpdate() {
        val newEntity = TestEntity(id = 1_000_000)

        em.merge(newEntity)
        em.flush()

        assertThat(newEntity.id).isEqualTo(1_000_000)
    }
}
