package com.aydemir.movieapp.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.aydemir.movieapp.NavGraphDirections
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.core.Constants
import com.aydemir.movieapp.databinding.FragmentMovieDetailBinding
import com.aydemir.movieapp.util.extensions.hide
import com.aydemir.movieapp.util.extensions.show
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        setListener()
        initObserver()
    }

    private fun init() {
        viewModel.id = args.id
    }

    private fun initAdapter() {
        val movieCastAdapter = MovieCastAdapter()
        binding.recyclerViewCast.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = movieCastAdapter
        }

        val movieGenreAdapter = MovieGenreAdapter()
        binding.recyclerViewGenre.apply {
            val manager = FlexboxLayoutManager(context)
            manager.flexDirection = FlexDirection.ROW
            layoutManager = manager
            adapter = movieGenreAdapter
        }

        val movieListAdapter = MovieListAdapter {
            when (it) {
                is MovieListAdapterEvent.ClickMovie -> {
                    it.data.id?.apply {
                        val action = NavGraphDirections.actionGlobalMovieDetailFragment(this)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        binding.recyclerViewMovieRecommendations.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = movieListAdapter
        }
    }

    private fun setListener() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //this is for one flow
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.uiState.collect {
                        when (it) {
                            is UiState.Success -> {
                                it.apply {
                                    binding.apply {
                                        imgBackDrop.load(
                                            Constants.Pref.BASE_IMG_URL + it.data.pathBackDrops
                                        )
                                        imgPoster.load(
                                            Constants.Pref.BASE_IMG_URL + it.data.pathPoster
                                        )
                                        txtMovieTitle.text = it.data.title
                                        txtMovieDesc.text = it.data.overview
                                        (recyclerViewGenre.adapter as MovieGenreAdapter).submitList(
                                            it.data.genres
                                        )
                                        if (it.data.cast?.isEmpty() == true) relativeCast.hide()
                                        (recyclerViewCast.adapter as MovieCastAdapter).submitList(it.data.cast)

                                        if (it.data.movieRecommendations?.isEmpty() == true) relativeMovieRecommendations.hide()
                                        (recyclerViewMovieRecommendations.adapter as MovieListAdapter).submitList(
                                            it.data.movieRecommendations
                                        )
                                    }
                                }
                                binding.progressBar.hide()
                                binding.nested.show()
                            }
                            is UiState.Error -> {
                                binding.progressBar.hide()
                            }
                            is UiState.Loading -> {
                                binding.progressBar.show()
                            }
                        }
                    }
                }
            }
        }
    }
}