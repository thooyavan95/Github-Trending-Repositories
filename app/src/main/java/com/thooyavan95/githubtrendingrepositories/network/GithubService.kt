package com.thooyavan95.githubtrendingrepositories.network

import com.thooyavan95.githubtrendingrepositories.entity.Repo
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("repositories")
    suspend fun fetchRepos(
        @Query("since") since : Long
    ) : List<Repo>
}