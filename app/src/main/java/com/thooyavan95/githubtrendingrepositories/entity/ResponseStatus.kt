package com.thooyavan95.githubtrendingrepositories.entity

sealed class ResponseStatus<out T : Any> {
    object Loading : ResponseStatus<Nothing>()
    data class Success<T : Any>(val data : T) : ResponseStatus<T>()
    data class Error(val exception : Exception) : ResponseStatus<Nothing>()
}