package com.aydemir.movieapp.util.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.hide() {
    if (visibility == View.GONE) return
    animate()
        ?.alpha(0f)
        ?.setDuration(250)
        ?.setListener(object : AnimatorListenerAdapter() {
            var isCanceled = false
            override fun onAnimationEnd(animation: Animator) {
                if (isCanceled) return
                visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                isCanceled = true
            }
        })
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    if (visibility == View.VISIBLE) return
    if (alpha == 1f) alpha = 0f
    animate()
        .alpha(1f)
        .setDuration(350)
        .setListener(object : AnimatorListenerAdapter() {
            var isCanceled = false
            override fun onAnimationStart(animation: Animator) {
                visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                isCanceled = true
            }
        })
}


fun String?.getDateFormatted(): String {
    val date = this?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it) }
    return date?.let {
        val calendar = Calendar.getInstance()
        calendar.time = it
        calendar.get(Calendar.YEAR).toString()
    } ?: ""
}