package com.example.task.utils

import android.content.Context
import android.net.ConnectivityManager

object Utility {

    const val otherErr = "Error Occurred!"


    fun isNetWorkAvaialable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //should check null because in airplane mode it will be null
        return netInfo != null && netInfo.isConnected
    }
}