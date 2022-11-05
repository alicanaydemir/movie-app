package com.aydemir.movieapp.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        setListener()
        initObserver()
    }

    private fun init() {

    }

    private fun initAdapter() {

    }

    private fun setListener() {
        binding.txtTitle.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment()
            findNavController().navigate(action)
        }
    }

    private fun initObserver() {

    }
}