package com.thooyavan95.githubtrendingrepositories.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.network.GithubService
import com.thooyavan95.githubtrendingrepositories.network.Retrofit
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_ITEM_ID = 0L

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(private val database : GithubRepoDB, private val apiService: GithubService) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {

        val nextID : Long = when(loadType){

            LoadType.REFRESH -> GITHUB_STARTING_ITEM_ID

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {

                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                lastItem.id
            }
        }

        try {

            val response = apiService.fetchRepos(nextID)

            val endOfPagination = response.isEmpty()

            database.withTransaction {

                if(loadType == LoadType.REFRESH){
                    database.repoDAO().clearRepo()
                }

                database.repoDAO().insertAllRepo(response)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)

        }catch (e : HttpException){

            return MediatorResult.Error(e)
        }catch (e : IOException){

            return MediatorResult.Error(e)
        }

    }
}