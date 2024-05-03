package com.example.core.common.outcome

/**
 * Defines the possible outcomes of a request for a resource.
 * Success, with the requested data, or Failure, with an error response.
 */
sealed class Outcome<out T> {
    fun fold(
        onSuccess: (T) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(errorResponse)
        }
    }
}
