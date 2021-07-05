package com.thooyavan95.githubtrendingrepositories.entity

sealed class UiStatus<out T : Any> {

    object Loading : UiStatus<Nothing>()
    data class Content<T : Any>(val content : T) : UiStatus<T>()
    data class Error(val exception: Exception) : UiStatus<Nothing>()
}