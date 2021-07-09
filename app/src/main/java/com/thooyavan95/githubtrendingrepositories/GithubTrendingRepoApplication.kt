package com.thooyavan95.githubtrendingrepositories

import android.app.Application
import com.thooyavan95.githubtrendingrepositories.network.Retrofit

class GithubTrendingRepoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Retrofit.init(this)
    }

}