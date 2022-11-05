package com.aydemir.movieapp.ui.movieDetail

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.databinding.FragmentMovieDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        setListener()
        initObserver()
    }

    private fun init() {
        viewModel.toString()
    }

    private fun initAdapter() {
        val backDropsAdapter = BackDropsAdapter()
        binding.viewPager.apply {
            adapter = backDropsAdapter
        }
    }

    private fun setListener() {

    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        // New value received
                        when (it) {
                            is UiState.Success -> {}
                            is UiState.Error -> {
                                Snackbar.make(
                                    binding.rootView,
                                    it.response.statusMessage,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is UiState.Loading -> {
                                binding.progressBar.isVisible = it.status
                            }
                        }
                    }
                }

                launch {
                    viewModel.imgBackDrops.collect {
                        (binding.viewPager.adapter as BackDropsAdapter).submitList(it?.backdrops)
                    }
                }
            }
        }
    }
}