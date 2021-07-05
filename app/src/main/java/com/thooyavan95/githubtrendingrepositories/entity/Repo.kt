package com.thooyavan95.githubtrendingrepositories.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey
    val id : Long,
    val name : String,
    val description : String?
)