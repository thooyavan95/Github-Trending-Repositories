package com.thooyavan95.githubtrendingrepositories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.thooyavan95.githubtrendingrepositories.network.GithubService
import com.thooyavan95.githubtrendingrepositories.network.Retrofit

class MainViewModel(application : Application) : AndroidViewModel(application) {

    var githubService : GithubService = Retrofit(application).getInstance()

}