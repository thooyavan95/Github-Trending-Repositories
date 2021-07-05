package com.thooyavan95.githubtrendingrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.thooyavan95.githubtrendingrepositories.R
import com.thooyavan95.githubtrendingrepositories.databinding.ItemFooterLoadstateBinding

class RepositoryLoadStateViewHolder(private val binding : ItemFooterLoadstateBinding,
                             retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

        fun bind(loadState: LoadState){

            if(loadState is LoadState.Error){
                binding.errorMessage.text = loadState.error.localizedMessage
            }

                binding.footerProgressBar.isVisible = loadState is LoadState.Loading
                binding.retryButton.isVisible = loadState is LoadState.Error
                binding.errorMessage.isVisible = loadState is LoadState.Error
        }


    companion object{

        fun create(parent : ViewGroup, retry: () -> Unit) : RepositoryLoadStateViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_footer_loadstate, parent, false)
            val binding = ItemFooterLoadstateBinding.bind(view)
            return RepositoryLoadStateViewHolder(binding, retry)
        }
    }
}