package com.aydemir.home

import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.aydemir.core.base.BaseFragment
import com.aydemir.core.extensions.hide
import com.aydemir.core.extensions.show
import com.aydemir.home.databinding.FragmentHomeBinding
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
                        val navOptions =
                            NavOptions.Builder()
                                .setEnterAnim(com.aydemir.core.R.anim.anim_slide_in_left)
                                .setExitAnim(com.aydemir.core.R.anim.anim_slide_out_left)
                                .setPopEnterAnim(com.aydemir.core.R.anim.anim_slide_out_right)
                                .setPopExitAnim(com.aydemir.core.R.anim.anim_slide_in_right)
                                .build()
                        val request = NavDeepLinkRequest.Builder
                            .fromUri("movieApp://movie-detail-screen/?id=$id".toUri())
                            .build()
                        findNavController().navigate(request, navOptions)
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
        homeListAdapter.saveStateRecyclerViews()
        super.onDestroyView()
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
            }
        }
    }
}