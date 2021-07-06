package com.thooyavan95.githubtrendingrepositories.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.thooyavan95.githubtrendingrepositories.R
import com.thooyavan95.githubtrendingrepositories.databinding.FragmentRepositoryListingBinding
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryAdapter
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu, menu)

        val menuItem = menu.findItem(R.id.action_search)
        binding?.searchView?.setMenuItem(menuItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = RepoViewModelFactory.getViewModel(this, requireActivity().application)

        repoAdapter = RepositoryAdapter()

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        observeRepoListLiveData()
        observeRepoListLoadState()
        
        binding?.searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })



    }

    private fun observeRepoListLiveData() {

        lifecycleScope.launch {
            viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer {
                repoAdapter.submitData(lifecycle, it)
            })
        }
    }


    private fun observeRepoListLoadState() {

        val header = RepositoryLoadStateAdapter{
            repoAdapter.retry()
        }

        binding?.recyclerView?.adapter =  repoAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = RepositoryLoadStateAdapter{
                repoAdapter.retry()
            }
        )

        repoAdapter.addLoadStateListener { loadState ->

            header.loadState = loadState.mediator?.refresh
                .takeIf { it is LoadState.Error && repoAdapter.itemCount > 0 }
                ?: loadState.prepend

            binding?.recyclerView?.isVisible = loadState.mediator?.refresh is LoadState.NotLoading || loadState.source.refresh is LoadState.NotLoading
            binding?.retryButton?.isVisible = loadState.mediator?.refresh is LoadState.Error && repoAdapter.itemCount == 0
            binding?.progressBar?.isVisible = loadState.mediator?.refresh is LoadState.Loading

            loadState.source.append as? LoadState.Error ?:
            loadState.source.prepend as? LoadState.Error ?:
            loadState.append as? LoadState.Error ?:
            loadState.prepend as? LoadState.Error

        }
    }

    private fun search(query : String?){

        query?.let {
            lifecycleScope.launch {
                viewModel.doSearch(query).collectLatest {
                    repoAdapter.submitData(it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}