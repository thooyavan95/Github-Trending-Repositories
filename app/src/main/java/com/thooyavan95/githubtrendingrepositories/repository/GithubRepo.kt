package com.thooyavan95.githubtrendingrepositories.repository

import com.thooyavan95.githubtrendingrepositories.entity.Repo
import com.thooyavan95.githubtrendingrepositories.entity.ResponseStatus
import com.thooyavan95.githubtrendingrepositories.network.Retrofit

class GithubRepo(private val listener : ResponseListener) {

    suspend fun getRepos(){

        try{
            val response = Retrofit.instance.fetchRepos()
            listener.listOfResponse(ResponseStatus.Success(response))
        }catch (e : Exception){
            listener.listOfResponse(ResponseStatus.Error(e))
        }

    }

}

interface ResponseListener{
    fun listOfResponse(response : ResponseStatus<List<Repo>>)
}