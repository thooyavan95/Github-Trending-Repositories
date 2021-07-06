package com.thooyavan95.githubtrendingrepositories.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo
import kotlinx.coroutines.launch

class RepoViewModel(private val githubRepo : GithubRepo) : ViewModel() {

    lateinit var repoListLiveData : LiveData<PagingData<Repo>>
    lateinit var repoSearchLiveData : LiveData<PagingData<Repo>>

    init {
        viewModelScope.launch {

            val response = githubRepo.getRepos()
            repoListLiveData = response.asLiveData().cachedIn(viewModelScope)
        }
    }

    fun doSearch(searchQuery : String){

        val response = githubRepo.searchRepo(query = searchQuery)
        repoSearchLiveData = response.asLiveData().cachedIn(viewModelScope)
    }

}