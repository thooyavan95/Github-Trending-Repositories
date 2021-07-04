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
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryAdapter

class RepositoryListingFragment : Fragment() {

    private var binding : FragmentRepositoryListingBinding? = null
    private val viewModel by viewModels<RepoViewModel>()
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

        repoAdapter = RepositoryAdapter()

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer {
            repoAdapter.updateRepoList(it)
        })

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}