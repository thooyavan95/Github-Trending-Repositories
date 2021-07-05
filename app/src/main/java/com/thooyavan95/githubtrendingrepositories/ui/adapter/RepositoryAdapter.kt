package com.thooyavan95.githubtrendingrepositories.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.thooyavan95.githubtrendingrepositories.entity.Repo

class RepositoryAdapter : PagingDataAdapter<Repo, RepositoryItemViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        return RepositoryItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {

        val repo = getItem(position)
        holder.bind(repo)
    }

    companion object{

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>(){

            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}