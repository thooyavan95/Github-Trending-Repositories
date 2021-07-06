package com.thooyavan95.githubtrendingrepositories.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.repository.GithubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RepoViewModel(private val githubRepo : GithubRepo) : ViewModel() {

    lateinit var repoListLiveData : LiveData<PagingData<Repo>>
    private var currentSearch : String? = null
    private var currentSearchResult : Flow<PagingData<Repo>>? = null

    init {
        viewModelScope.launch {

            val response = githubRepo.getRepos()
            repoListLiveData = response.asLiveData().cachedIn(viewModelScope)
        }
    }

    fun doSearch(searchQuery : String) : Flow<PagingData<Repo>> {

        val lastResult = currentSearchResult

        if(searchQuery == currentSearch && lastResult != null){
            return lastResult
        }

        currentSearch = searchQuery

        val result = githubRepo.searchRepo(query = searchQuery).cachedIn(viewModelScope)
        currentSearchResult = result
        return result
    }

}