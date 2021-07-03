package com.thooyavan95.githubtrendingrepositories.network

import com.thooyavan95.githubtrendingrepositories.entity.Repo
import retrofit2.http.GET

interface GithubService {

    @GET("repositories")
    suspend fun fetchRepos() : List<Repo>
}