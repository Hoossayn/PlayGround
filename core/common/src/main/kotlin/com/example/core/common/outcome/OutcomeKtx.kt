package com.example.core.common.outcome

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Filters and maps a [Flow<Outcome<T>>] to T value, ignoring any failures.
 */
fun <T> Flow<Outcome<T>>.success(): Flow<T> = this
    .filter { it is Success }
    .map { (it as Success).invoke() }

/**
 * Maps a [Flow<Outcome<T>>] to a [Flow<OutCome<R>>]. Allows any successful Outcome to be mutated.
 */
inline fun <T, reified R> Flow<Outcome<T>>.mapSuccess(
    crossinline onSuccess: (T) -> R,
): Flow<Outcome<R>> = this
    .map {
        when (it) {
            is Success -> Success(onSuccess(it()))
            is Failure -> it
        }
    }

/**
 * Maps a [Flow<Outcome<T>>]  to a [Flow<Outcome<R>>]. Successful response can be mapped to any [Outcome]
 */
inline fun <T, R> Flow<Outcome<T>>.mapOutcome(
    crossinline onSuccess: (Success<T>) -> Outcome<R>,
): Flow<Outcome<R>> = this
    .map {
        when (it) {
            is Success -> onSuccess(it)
            is Failure -> it
        }
    }

inline fun <T> Flow<Outcome<T>>.onSuccess(
    crossinline action: suspend (data: T) -> Unit,
): Flow<Outcome<T>> = this
    .onEach { if (it is Success) action(it.data) }

inline fun <T> Flow<Outcome<T>>.onFailure(
    crossinline action: suspend (
        errorResponse: String,
        errorCode: Int,
        throwable: Throwable?,
    ) -> Unit,
): Flow<Outcome<T>> = this
    .onEach { if (it is Failure) action(it.errorResponse, it.errorCode, it.throwable) }
