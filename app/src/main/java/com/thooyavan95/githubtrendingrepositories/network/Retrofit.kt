package com.thooyavan95.githubtrendingrepositories.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private const val BASE_URL = "https://api.github.com/"

    private val retrofitInstance = Retrofit.Builder()
        .apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    val instance : GithubService = retrofitInstance.create(GithubService::class.java)
}