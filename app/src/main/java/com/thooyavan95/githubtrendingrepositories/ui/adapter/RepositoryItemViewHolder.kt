package com.thooyavan95.githubtrendingrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thooyavan95.githubtrendingrepositories.R
import com.thooyavan95.githubtrendingrepositories.databinding.ItemRepoBinding
import com.thooyavan95.githubtrendingrepositories.entity.Repo

class RepositoryItemViewHolder(private val binding : ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repo : Repo?){

        if(repo != null){

            binding.itemRepoId.text = repo.id.toString()
            binding.itemRepoName.text = repo.name
        }
    }

    companion object{

        fun create(parent : ViewGroup) : RepositoryItemViewHolder{

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo, parent, false)
            val binding = ItemRepoBinding.bind(view)
            return RepositoryItemViewHolder(binding)
        }
    }

}