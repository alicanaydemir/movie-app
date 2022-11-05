package com.aydemir.movieapp.util.helper

import android.content.SharedPreferences

class SharedHelper(private val sharedPreferences: SharedPreferences) {

    fun putBooleanData(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanData(key: String, defVal: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defVal)
    }

    fun putIntData(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntData(key: String, defVal: Int): Int {
        return sharedPreferences.getInt(key, defVal)
    }

    fun putFloatData(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloatData(key: String, defVal: Float): Float {
        return sharedPreferences.getFloat(key, defVal)
    }

    fun putStringData(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringData(key: String, defVal: String?): String? {
        return sharedPreferences.getString(key, defVal)
    }

    fun getSharedPref(): SharedPreferences {
        return sharedPreferences
    }

    /**
     * Check shared preferences contains given key
     */
    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Deletes specific data in [SharedPreferences]
     */
    fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Clear all shared preferences
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}