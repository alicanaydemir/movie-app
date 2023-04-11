package com.aydemir.favorites

import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aydemir.core.base.BaseFragment
import com.aydemir.core.extensions.hide
import com.aydemir.core.extensions.show
import com.aydemir.favorites.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()
    private val favoriteMovieListAdapter: FavoriteMovieListAdapter by lazy {
        FavoriteMovieListAdapter {
            when (it) {
                is FavoriteMovieListAdapterEvent.ClickMovie -> {
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
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = favoriteMovieListAdapter
    }

    private fun setListener() {

    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //this is for one flow
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.uiStateFavorites.collect {
                        when (it) {
                            is UiStateFavorites.Success -> {
                                it.apply {
                                    favoriteMovieListAdapter.submitList(it.data)
                                }
                                binding.progressBar.hide()
                                binding.recyclerView.show()
                                binding.txtTitleEmpty.hide()
                            }
                            is UiStateFavorites.Empty -> {
                                favoriteMovieListAdapter.submitList(emptyList())
                                binding.progressBar.hide()
                                binding.txtTitleEmpty.show()
                            }
                            is UiStateFavorites.Error -> {
                                binding.progressBar.hide()
                                binding.txtTitleEmpty.hide()
                            }
                            is UiStateFavorites.Loading -> {
                                binding.progressBar.show()
                                binding.txtTitleEmpty.hide()
                            }
                            is UiStateFavorites.NoConnection -> {
                                binding.progressBar.hide()
                                binding.txtTitleEmpty.hide()
                            }
                        }
                    }
                }
            }
        }
    }
}