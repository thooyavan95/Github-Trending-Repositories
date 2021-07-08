package com.thooyavan95.githubtrendingrepositories.ui

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.thooyavan95.githubtrendingrepositories.R
import com.thooyavan95.githubtrendingrepositories.databinding.FragmentRepositoryListingBinding
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryAdapter
import com.thooyavan95.githubtrendingrepositories.ui.adapter.RepositoryLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RepositoryListingFragment : Fragment() {


    companion object{
        private const val LAST_SEARCH_QUERY = "last search"
    }

    private var binding : FragmentRepositoryListingBinding? = null
    private lateinit var viewModel : RepoViewModel
    private lateinit var repoAdapter : RepositoryAdapter
    private var searchQuery : String? = null
    private var searchJob : Job? = null
    private var listingJob : Job? = null

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

        initAdapter()

        setSwipeRefreshColors()
        setListenerToRetry()
        setListenerToSwipeRefresh()
        observeRepoListIfQueryNull(savedInstanceState)

        observeRepoListLoadState()
        searchQueryListener()
        observeBackPressed()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, searchQuery)
    }

    private fun initAdapter(){

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding?.recyclerView?.addItemDecoration(decoration)

        repoAdapter = RepositoryAdapter()

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

    }

    private fun setListenerToSwipeRefresh(){

        binding?.swipeRefresh?.setOnRefreshListener {

            binding?.let{
                if(it.searchView.isSearchOpen){
                    it.searchView.closeSearch()
                }
            }

            observeRepoListLiveData()
        }
    }

    private fun searchQueryListener(){

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

    private fun setListenerToRetry(){

        binding?.retryButton?.setOnClickListener {
            repoAdapter.retry()
        }
    }

    private fun observeRepoListIfQueryNull(savedInstanceState: Bundle?){

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)

        if(query != null){
            search(query)
        }else{
            observeRepoListLiveData()
        }
    }

    private fun observeRepoListLiveData() {

        listingJob?.cancel()
        listingJob = lifecycleScope.launch {
            viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer {
                repoAdapter.submitData(lifecycle, it)

                binding?.let { binding ->
                    if(binding.swipeRefresh.isRefreshing){
                        binding.swipeRefresh.isRefreshing = false
                    }
                }

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

            binding?.recyclerView?.isVisible = (loadState.mediator?.refresh is LoadState.NotLoading || loadState.source.refresh is LoadState.NotLoading) && repoAdapter.itemCount != 0
            binding?.retryButton?.isVisible = loadState.mediator?.refresh is LoadState.Error && repoAdapter.itemCount == 0
            binding?.progressBar?.isVisible = loadState.mediator?.refresh is LoadState.Loading

            loadState.source.append as? LoadState.Error ?:
            loadState.source.prepend as? LoadState.Error ?:
            loadState.append as? LoadState.Error ?:
            loadState.prepend as? LoadState.Error

        }
    }

    private fun collectSearchResultsFlow(query : String){

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.doSearch(query).collectLatest {
                repoAdapter.submitData(it)
            }
        }
    }

    private fun search(query : String?){

        query?.let {
            searchQuery = query
            collectSearchResultsFlow(query)
        }
    }

    private fun setSwipeRefreshColors(){

        binding?.swipeRefresh?.apply {
            setProgressBackgroundColorSchemeResource(R.color.blue)
            setColorSchemeResources(R.color.white)
        }
    }

    private fun observeBackPressed(){

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                binding?.let {
                    if(it.searchView.isSearchOpen){
                        it.searchView.closeSearch()
                    }else{
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }

            }
        })
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