package com.thooyavan95.githubtrendingrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thooyavan95.githubtrendingrepositories.databinding.FragmentRepositoryListingBinding

class RepositoryListingFragment : Fragment() {

    private var binding : FragmentRepositoryListingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryListingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}