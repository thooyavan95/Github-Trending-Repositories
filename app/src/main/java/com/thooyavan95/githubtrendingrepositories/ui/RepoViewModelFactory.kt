
package com.thooyavan95.githubtrendingrepositories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thooyavan95.githubtrendingrepositories.MainActivity
import com.thooyavan95.githubtrendingrepositories.db.GithubRepoDB
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo

class RepoViewModelFactory(private val activity: MainActivity) : ViewModelProvider.AndroidViewModelFactory(activity.application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val db = GithubRepoDB.getInstance(activity.application)
        val retrofitInstance =  activity.getRetrofitInstance()
        val githubRepo = GithubRepo(db, retrofitInstance)
        return RepoViewModel(githubRepo) as T
    }


}