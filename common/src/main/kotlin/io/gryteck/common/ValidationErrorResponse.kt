package io.gryteck.common

data class ValidationErrorResponse(
    override val message: String?,
    val errors: Map<String, FieldMessage> = emptyMap()
): ErrorResponse(message) {
    data class FieldMessage(
        val rejectedValue: Any?,
        val defaultMessage: String?
    )
}
