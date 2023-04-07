package com.aydemir.settings.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.aydemir.core.base.BaseFragment
import com.aydemir.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModels()

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