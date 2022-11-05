package com.aydemir.movieapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.aydemir.movieapp.core.BaseFragment
import com.aydemir.movieapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()

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

    }

    private fun initObserver() {

    }
}