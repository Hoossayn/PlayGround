package com.example.core.common.outcome

/**
 * Called when the given request is successful.
 *
 * @param T is object Type
 * @property data is the object requested from backend
 */
open class Success<out T>(val data: T) : Outcome<T>() {
    operator fun invoke(): T = data

    override fun equals(other: Any?): Boolean {
        return (other as? Success<*>)?.data?.equals(this.data) == true
    }

    override fun hashCode(): Int {
        return data?.hashCode() ?: 0
    }
}
