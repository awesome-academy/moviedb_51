package com.sunasterisk.moviedb_51.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {
    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnectedOrConnecting
        }
    }
}
