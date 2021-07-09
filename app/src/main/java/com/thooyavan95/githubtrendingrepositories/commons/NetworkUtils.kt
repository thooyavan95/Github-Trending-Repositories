package com.thooyavan95.githubtrendingrepositories.commons

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils{

    fun isNetworkAvailable(context: Context) : Boolean{

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting == true

    }

}