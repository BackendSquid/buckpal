package backendsquid.buckpal.common

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component


@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class WebAdapter(

    /**
     * 이 값은 autodetected 대상인 컴포넌트의 경우,
     * 컴포넌트 이름을 스프링 빈으로 변경하라는 제안을 나타낼 수 있다.
     * @return 제안된 컴포넌트 이름(있는 경우), 없다면 빈 문자열
     */
//    @AliasFor(annotation = Component::class) val value: String = ""
)