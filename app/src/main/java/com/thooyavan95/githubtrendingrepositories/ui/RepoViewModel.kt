package com.thooyavan95.githubtrendingrepositories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thooyavan95.githubtrendingrepositories.db.GithubRepoDB
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.entity.ResponseStatus
import com.thooyavan95.githubtrendingrepositories.entity.UiStatus
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo
import com.thooyavan95.githubtrendingrepositories.repository.ResponseListener
import kotlinx.coroutines.launch

class RepoViewModel(private val githubRepo : GithubRepo) : ViewModel(), ResponseListener {

    private val _repoListMLD = MutableLiveData<UiStatus<List<Repo>>>()
    val repoListLiveData : LiveData<UiStatus<List<Repo>>>
        get() = _repoListMLD


    init {
        viewModelScope.launch {
            _repoListMLD.postValue(UiStatus.Loading)
            githubRepo.getRepos()
        }
    }

    override fun listOfResponse(response : ResponseStatus<List<Repo>>) {

        when(response){

            is ResponseStatus.Success -> _repoListMLD.postValue(UiStatus.Content(response.data))

            is ResponseStatus.Error -> _repoListMLD.postValue(UiStatus.Error(response.exception))
        }
    }
}