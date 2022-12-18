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
import com.aydemir.movieapp.R
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.core.Constants
import com.aydemir.movieapp.databinding.FragmentMovieDetailBinding
import com.aydemir.movieapp.util.extensions.getDateFormatted
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

        val movieListHorizontalAdapter = MovieListHorizontalAdapter {
            when (it) {
                is MovieListHorizontalAdapterEvent.ClickMovie -> {
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
            adapter = movieListHorizontalAdapter
        }
    }

    private fun setListener() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgFavorite.setOnClickListener {
            viewModel.doFavorite()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.uiStateMovieDetail.collect {
                        when (it) {
                            is UiStateMovieDetail.Success -> {
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
                                        else relativeCast.show()
                                        (recyclerViewCast.adapter as MovieCastAdapter).submitList(it.data.cast)

                                        if (it.data.movieRecommendations?.isEmpty() == true) relativeMovieRecommendations.hide()
                                        else relativeMovieRecommendations.show()
                                        (recyclerViewMovieRecommendations.adapter as MovieListHorizontalAdapter).submitList(
                                            it.data.movieRecommendations
                                        )
                                        txtReleaseDate.text =
                                            it.data.releaseDate?.getDateFormatted()
                                    }
                                }
                                binding.progressBar.hide()
                                binding.nested.show()
                            }
                            is UiStateMovieDetail.Error -> {
                                binding.txtTitleError.text = it.response.statusMessage
                                binding.progressBar.hide()
                                binding.txtTitleError.show()
                            }
                            is UiStateMovieDetail.Loading -> {
                                binding.nested.hide()
                                binding.txtTitleError.hide()
                                binding.progressBar.show()
                            }
                        }
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.isFavorite.collect {
                        if (it) {
                            binding.imgFavorite.setImageResource(R.drawable.ic_bookmark)
                        } else {
                            binding.imgFavorite.setImageResource(R.drawable.ic_bookmark_border)
                        }
                    }
                }
            }
        }
    }
}