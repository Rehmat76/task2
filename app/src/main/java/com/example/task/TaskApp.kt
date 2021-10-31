package com.example.task

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.task.receivers.NetworkReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApp : MultiDexApplication() {


    fun setNetworkListener(listener: NetworkReceiver.ConnectivityReceiverListener) {
        NetworkReceiver.connectivityReceiverListener = listener
    }

}