package com.aydemir.movieapp.util.extensions

import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

/*fun ImageView.loadImage(url: String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/w500/$url")
        .into(this)
}*/