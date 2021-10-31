package com.example.task.ui

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.R
import com.example.task.receivers.NetworkReceiver
import dagger.hilt.android.AndroidEntryPoint
import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context

import com.example.task.receivers.MyAlarm

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.task.databinding.ActivityMainBinding
import java.util.*

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    private var mNetworkReceiver: BroadcastReceiver? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNetworkReceiver = NetworkReceiver()
        registerNetworkBroadcastForNougat()

        setNotificationForDrink()

        navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setupWithNavController(navController)

    }


    private fun registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkChanges()
    }


    private fun unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun setNotificationForDrink() {
        val alarmMgr: AlarmManager =
            application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(applicationContext, MyAlarm::class.java)
        i.putExtra("showNotification", true)
        val alarmIntent: PendingIntent = PendingIntent.getBroadcast(applicationContext, 0, i, 0)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.HOUR_OF_DAY, 2)
//        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 3)
        calendar.set(Calendar.MINUTE, 40)
// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                alarmMgr.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmIntent
                )
            }
            else -> {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
            }
        }
    }

}