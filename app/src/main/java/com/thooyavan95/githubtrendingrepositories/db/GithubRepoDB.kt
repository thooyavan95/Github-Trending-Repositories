package com.thooyavan95.githubtrendingrepositories.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thooyavan95.githubtrendingrepositories.entity.OwnerConverter
import com.thooyavan95.githubtrendingrepositories.entity.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
@TypeConverters(OwnerConverter::class)
abstract class GithubRepoDB : RoomDatabase() {

    abstract fun repoDAO() : RepoDAO

    companion object{

        private const val DB_NAME = "github_repo_db"
        @Volatile
        private var INSTANCE : GithubRepoDB? = null

        fun getInstance(context: Context) : GithubRepoDB{

                synchronized(this){

                    var instance = INSTANCE

                    if(instance == null){
                        instance = Room.databaseBuilder(context, GithubRepoDB::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()

                        INSTANCE = instance
                    }

                    return instance
                }
        }

    }

}