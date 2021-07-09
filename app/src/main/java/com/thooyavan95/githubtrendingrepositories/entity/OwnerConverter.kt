package com.thooyavan95.githubtrendingrepositories.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class OwnerConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromOwner(owner : Owner) : String{
        return gson.toJson(owner)
    }

    @TypeConverter
    fun toOwner(ownerString : String) : Owner{
        return gson.fromJson(ownerString, Owner::class.java)
    }

}