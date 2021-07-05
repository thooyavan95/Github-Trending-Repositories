
package com.thooyavan95.githubtrendingrepositories.ui

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thooyavan95.githubtrendingrepositories.db.GithubRepoDB
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo

object RepoViewModelFactory {

    fun getViewModel(fragment : Fragment, application: Application) : RepoViewModel{

        return ViewModelProvider(fragment, object : ViewModelProvider.AndroidViewModelFactory(application) {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val db = GithubRepoDB.getInstance(application)
                val githubRepo = GithubRepo(db)
                return RepoViewModel(githubRepo) as T
            }
        })[RepoViewModel::class.java]
    }


}