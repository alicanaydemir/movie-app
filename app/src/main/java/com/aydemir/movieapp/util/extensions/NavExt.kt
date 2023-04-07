package com.aydemir.movieapp.util.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.aydemir.movieapp.R
import com.aydemir.movieapp.databinding.ActivityMainBinding

fun setVisibilityBottom(destinationId: Int, binding: ActivityMainBinding) {

    when (destinationId) {
        R.id.homeFragment, com.aydemir.settings.R.id.settingsFragment, R.id.favoritesFragment -> {
            showView(binding)
        }

        else -> {
            hideView(binding)
        }
    }
}

private fun hideView(binding: ActivityMainBinding) {
    val height =
        binding.bottomNavigationView.context.resources.getDimensionPixelSize(R.dimen.bottom_height)
    binding.bottomNavigationView.animate()
        ?.translationY(height.toFloat())
        ?.setInterpolator(FastOutLinearInInterpolator())
        ?.setDuration(150)
        ?.setListener(object : AnimatorListenerAdapter() {
            var isCanceled = false
            override fun onAnimationEnd(animation: Animator) {
                if (isCanceled) return
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                isCanceled = true
            }
        })
}

private fun showView(binding: ActivityMainBinding) {
    binding.bottomNavigationView.animate()
        ?.translationY(0F)
        ?.setInterpolator(LinearOutSlowInInterpolator())
        ?.setStartDelay(200)
        ?.setDuration(150)
        ?.setListener(object : AnimatorListenerAdapter() {
            var isCanceled = false
            override fun onAnimationEnd(animation: Animator) {
                if (isCanceled) return
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                isCanceled = true
            }
        })
}