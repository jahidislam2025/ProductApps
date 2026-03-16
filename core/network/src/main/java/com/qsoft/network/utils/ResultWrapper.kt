package com.qsoft.network.utils

sealed class ResultWrapper<out T, out E> {
    data class Success<T>(val data: T) : ResultWrapper<T, Nothing>()
    data class Failure<E>(val error: E) : ResultWrapper<Nothing, E>()
}