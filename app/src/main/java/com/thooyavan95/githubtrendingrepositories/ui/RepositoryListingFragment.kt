package com.thooyavan95.githubtrendingrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thooyavan95.githubtrendingrepositories.databinding.FragmentRepositoryListingBinding
import com.thooyavan95.githubtrendingrepositories.entity.UiStatus
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryAdapter

class RepositoryListingFragment : Fragment() {

    private var binding : FragmentRepositoryListingBinding? = null
    private lateinit var viewModel : RepoViewModel
    private lateinit var repoAdapter : RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryListingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = RepoViewModelFactory.getViewModel(this, requireActivity().application)

        repoAdapter = RepositoryAdapter()

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer { uiStatus ->

            when(uiStatus){

                is UiStatus.Loading -> {
                    binding?.progressBar?.show()
                    binding?.retryButton?.visibility = View.GONE
                }
                is UiStatus.Content -> {
                    repoAdapter.updateRepoList(uiStatus.content)
                    binding?.progressBar?.hide()
                    binding?.retryButton?.visibility = View.GONE
                }
                is UiStatus.Error -> {
                    binding?.progressBar?.hide()
                    binding?.retryButton?.visibility = View.VISIBLE
                }

            }

        })

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}