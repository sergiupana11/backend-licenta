package org.unstpb.wheelshare.annotations

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern

class EnumValidator : ConstraintValidator<ValidatedEnum, Enum<*>> {
    private lateinit var pattern: Pattern

    override fun initialize(annotation: ValidatedEnum) {
        pattern = Pattern.compile(annotation.regex)
    }

    override fun isValid(
        value: Enum<*>?,
        context: ConstraintValidatorContext,
    ): Boolean {
        if (value == null) {
            return true
        }

        return pattern.matcher(value.name).matches()
    }
}
