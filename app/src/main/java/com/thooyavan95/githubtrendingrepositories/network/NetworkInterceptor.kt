package com.thooyavan95.githubtrendingrepositories.network

import android.content.Context
import com.thooyavan95.githubtrendingrepositories.commons.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context : Context) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        if(!NetworkUtils.isNetworkAvailable(context)){
            throw NetworkConnectivityException()
        }

        val builder =  chain.request().newBuilder()
        return chain.proceed(builder.build())

    }
}