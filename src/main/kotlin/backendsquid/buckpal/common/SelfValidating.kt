package backendsquid.buckpal.common

import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.Validator

abstract class SelfValidating<T>(
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator,
) {

    protected fun validateSelf() {
        val violations = validator.validate(this)

        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }

}
