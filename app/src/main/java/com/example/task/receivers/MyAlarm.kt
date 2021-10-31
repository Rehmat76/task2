package com.example.task.receivers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.task.database.DrinksDao
import com.example.task.database.DrinksLocalDataSource
import com.example.task.repositories.DrinksRepositories
import com.example.task.ui.MainActivity
import com.example.task.utils.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*


class MyAlarm : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
//        val showNotification = intent.getBooleanExtra("showNotification", false)

//        if (showNotification && getCurrentTime() == "14:00")
//        if (showNotification && getCurrentTime() == "15:40")
            sendNotification(context)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val calendar: Calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("HH:mm")
        val strDate = "" + mdformat.format(calendar.time)
        return strDate
    }

    private fun sendNotification(context: Context) {

        val notificationHelper = NotificationHelper(context)
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val contentIntent = PendingIntent.getActivity(
            context,
            33,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = notificationHelper.createNotificationBuilder(
            "Need some drinks open app now",
            "Margareta is one of the best drinks...", true, contentIntent
        )


        notificationHelper.makeNotification(builder, 33)

    }


}
