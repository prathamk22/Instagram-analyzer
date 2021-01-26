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
        const val USER_NAME = "userName"
        const val FULL_NAME = "fullName"
        const val USER_PROFILE_IMG = "userProfileImg"
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

    var userName: String
        get() = sharedPrefs.getString(USER_NAME, "")?:""
        set(value) {
            sharedPrefs.save(USER_NAME, value)
        }

    var fullName: String
        get() = sharedPrefs.getString(FULL_NAME, "")?:""
        set(value) {
            sharedPrefs.save(FULL_NAME, value)
        }

    var userProfileImg: String
        get() = sharedPrefs.getString(USER_PROFILE_IMG, "")?:""
        set(value) {
            sharedPrefs.save(USER_PROFILE_IMG, value)
        }

}