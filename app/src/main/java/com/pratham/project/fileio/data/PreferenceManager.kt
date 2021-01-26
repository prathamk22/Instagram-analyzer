package com.pratham.project.fileio.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.pratham.project.fileio.utils.save

class PreferenceManager constructor(
    context: Context
) {

    companion object{
        const val SHARED_PREFS_NAME: String = "instagram-prefs"
        const val LOGIN_COOKIES = "loginCookies"
        const val USER_AGENT = "userAgent"
    }

    private val sharedPrefs: SharedPreferences

    init {
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
    }

    var loginCookies: String
        get() = sharedPrefs.getString(LOGIN_COOKIES, "")?:""
        set(value) {
            sharedPrefs.save(LOGIN_COOKIES, value)
        }

    var userAgent: String
        get() = sharedPrefs.getString(USER_AGENT, "")?:""
        set(value) {
            sharedPrefs.save(USER_AGENT, value)
        }

}