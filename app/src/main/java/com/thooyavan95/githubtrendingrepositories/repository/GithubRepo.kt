package com.thooyavan95.githubtrendingrepositories.repository

import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.network.Retrofit

class GithubRepo(private val listener : ResponseListener) {

    suspend fun getRepos(){

        val response = Retrofit.instance.fetchRepos()
        listener.listOfResponse(response)
    }

}

interface ResponseListener{
    fun listOfResponse(repos : List<Repo>)
}