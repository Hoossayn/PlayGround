package com.example.core.common.outcome

/**
 * Called when the given request fails to make a request
 *
 * @property errorResponse is the  error message returned
 * @property errorCode is the HTTP error code
 *
 * @property throwable exception stack trace if the failure resulted from an exception
 */
data class Failure(
    val errorResponse: String,
    val errorCode: Int = -1,
    val throwable: Throwable? = null,
) : Outcome<Nothing>()
