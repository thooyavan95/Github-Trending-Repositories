package com.thooyavan95.githubtrendingrepositories.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thooyavan95.githubtrendingrepositories.entity.Repo

class RepositoryAdapter : RecyclerView.Adapter<RepositoryItemViewHolder>() {

    private val repoList : MutableList<Repo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        return RepositoryItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int {

        return if(repoList.isEmpty()) 0 else repoList.size
    }

    fun updateRepoList(listOfRepos : List<Repo>){
        repoList.addAll(listOfRepos)
        notifyDataSetChanged()
    }

}