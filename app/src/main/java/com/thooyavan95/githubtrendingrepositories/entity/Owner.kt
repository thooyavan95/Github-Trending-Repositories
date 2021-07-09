package com.thooyavan95.githubtrendingrepositories.entity

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login")
    val repoOwnerName : String
    )