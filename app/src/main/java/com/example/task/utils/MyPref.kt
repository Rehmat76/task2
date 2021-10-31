package com.example.task.utils

import android.content.Context

import android.content.SharedPreferences
import com.example.task.R


class MyPref private constructor(context: Context) {

    var searchBy: String?
        get() = pref!!.getString("SearchBy", "")
        set(searchBy) {
            pref!!.edit().putString("SearchBy", searchBy).apply()
        }

    fun setIsFirstTimeOpen() {
        pref!!.edit().putBoolean("FirstTimeOpen", false).apply()
    }

    val isFirstTimeOpen: Boolean
        get() = pref!!.getBoolean("FirstTimeOpen", true)

    fun setAppPurchased() {
        pref!!.edit().putBoolean("AppPurchased", true).apply()
    }

    val appPurchased: Boolean
        get() = pref!!.getBoolean("AppPurchased", false)
    var language: String?
        get() = pref!!.getString("DefaultLanguage", "en")
        set(language) {
            pref!!.edit().putString("DefaultLanguage", language).apply()
        }

    companion object {
        private var pref_instance: MyPref? = null
        private var pref: SharedPreferences? = null
        fun getInstance(context: Context): MyPref? {
            if (pref_instance == null || pref == null) {
                pref_instance = MyPref(context)
            }
            return pref_instance
        }
    }

    init {
        pref = context.getSharedPreferences(context.resources.getString(R.string.app_name), 0)
    }
}
