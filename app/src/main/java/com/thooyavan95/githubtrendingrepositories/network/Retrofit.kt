package com.thooyavan95.githubtrendingrepositories.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    lateinit var instance : GithubService

    private const val BASE_URL = "https://api.github.com/"

    fun init(context : Context){

        val httpclient = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .build()

        val retrofitInstance = Retrofit.Builder()
            .apply {
                baseUrl(BASE_URL)
                addConverterFactory(GsonConverterFactory.create())
                client(httpclient)
            }.build()

        instance = retrofitInstance.create(GithubService::class.java)
    }




}