package backendsquid.buckpal.common

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation

abstract class SelfValidating<T> {
    private val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    protected fun validateSelf() {
        val violations : Set<ConstraintViolation<T>> = validator.validate(this as T)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}