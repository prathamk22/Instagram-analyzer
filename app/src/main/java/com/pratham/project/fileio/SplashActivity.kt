package com.pratham.project.fileio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.utils.LOGIN_URI
import com.pratham.project.fileio.utils.base.BaseActivity

class SplashActivity : BaseActivity() {

    private val prefsManager: PreferenceManager by lazy {
        PreferenceManager(context = applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (prefsManager.loginCookies.isNullOrEmpty()){
            try {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(LOGIN_URI))
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }else{
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        finish()
    }
}