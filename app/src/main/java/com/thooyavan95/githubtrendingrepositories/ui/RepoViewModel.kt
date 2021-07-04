package com.thooyavan95.githubtrendingrepositories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo
import com.thooyavan95.githubtrendingrepositories.repository.ResponseListener
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel(), ResponseListener {

    private val _repoListMLD = MutableLiveData<List<Repo>>()
    val repoListLiveData : LiveData<List<Repo>>
        get() = _repoListMLD

    private val githubRepo = GithubRepo(this)

    init {
        viewModelScope.launch {
            githubRepo.getRepos()
        }
    }

    override fun listOfResponse(repos : List<Repo>) {
        _repoListMLD.postValue(repos)
    }
}