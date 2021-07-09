package com.thooyavan95.githubtrendingrepositories.network

import java.io.IOException

class NetworkConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {

        return "No Network Connection"
    }

}