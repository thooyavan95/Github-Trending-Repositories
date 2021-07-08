package com.thooyavan95.githubtrendingrepositories.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thooyavan95.githubtrendingrepositories.db.GithubRemoteMediator
import com.thooyavan95.githubtrendingrepositories.db.GithubRepoDB
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class GithubRepo(private val database : GithubRepoDB) {

    companion object{
        private const val PAGING_SIZE = 30
    }

    fun getRepos() : Flow<PagingData<Repo>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),

            remoteMediator = GithubRemoteMediator(database),

            pagingSourceFactory = {database.repoDAO().getAllRepos()}
        ).flow

    }

    fun searchRepo(query : String) : Flow<PagingData<Repo>> {

        val dbQuery = "%${query.replace(" ", "%")}%"

        return Pager(
            config = PagingConfig(
                PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { database.repoDAO().getReposBySearch(dbQuery)}
        ).flow

    }

}