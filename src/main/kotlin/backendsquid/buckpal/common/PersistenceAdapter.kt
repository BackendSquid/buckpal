package backendsquid.buckpal.common

import org.springframework.stereotype.Component

@Target(allowedTargets = [AnnotationTarget.TYPE, AnnotationTarget.CLASS])
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class PersistenceAdapter
