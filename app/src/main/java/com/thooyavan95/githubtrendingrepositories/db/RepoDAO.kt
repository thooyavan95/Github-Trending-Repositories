package com.thooyavan95.githubtrendingrepositories.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thooyavan95.githubtrendingrepositories.entity.Repo

@Dao
interface RepoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepo(repos : List<Repo>)

    @Query("SELECT * FROM repos")
    fun getAllRepos() : PagingSource<Int, Repo>

    @Query("DELETE FROM repos")
    suspend fun clearRepo()

    @Query("SELECT * FROM repos WHERE name LIKE :query OR description LIKE :query")
    fun getReposBySearch(query : String) : PagingSource<Int, Repo>

}