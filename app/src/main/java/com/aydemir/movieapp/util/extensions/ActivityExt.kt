package com.aydemir.movieapp.util.extensions

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.restartApp() {
    startActivity(Intent(this, T::class.java))
    finishAffinity()
}