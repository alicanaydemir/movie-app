package com.aydemir.movieapp.ui.home

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aydemir.movieapp.NavGraphDirections
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.core.Constants
import com.aydemir.movieapp.databinding.FragmentHomeBinding
import com.aydemir.movieapp.util.extensions.hide
import com.aydemir.movieapp.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val homeListAdapter: HomeListAdapter by lazy {
        HomeListAdapter {
            when (it) {
                is HomeListAdapterEvent.ClickMovie -> {
                    it.data.id?.let { id ->
                        val action = NavGraphDirections.actionGlobalMovieDetailFragment(
                            id
                        )
                        findNavController().navigate(action)
                    }
                }
                is HomeListAdapterEvent.ClickHeader -> {

                }
            }
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        setListener()
        initObserver()
    }

    private fun init() {

    }

    private fun initAdapter() {
        binding.recyclerView.adapter = homeListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeListAdapter.saveStateRecyclerViews()
        viewModel.saveState(homeListAdapter.scrollStates)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        homeListAdapter.saveStateRecyclerViews()
        viewModel.saveState(homeListAdapter.scrollStates)
    }

    private fun setListener() {

    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //this is for one flow
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.uiState.collect {
                        when (it) {
                            is UiStateHome.Success -> {
                                it.apply {
                                    homeListAdapter.submitList(it.data.listHomeItem)
                                }
                                binding.progressBar.hide()
                                binding.recyclerView.show()
                            }
                            is UiStateHome.Error -> {
                                binding.progressBar.hide()
                            }
                            is UiStateHome.Loading -> {
                                binding.progressBar.show()
                            }
                            is UiStateHome.NoConnection -> {

                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.state.getStateFlow<MutableMap<Int, Parcelable?>>(
                        Constants.Key.STATE,
                        mutableMapOf()
                    ).collect {
                        homeListAdapter.scrollStates = it
                    }
                }
            }
        }
    }
}