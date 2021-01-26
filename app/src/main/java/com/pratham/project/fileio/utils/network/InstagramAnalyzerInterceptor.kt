package com.pratham.project.fileio.utils.network

import android.content.Context
import com.pratham.project.fileio.data.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class InstagramAnalyzerInterceptor(val context: Context) : Interceptor {

    private val prefsManager: PreferenceManager = PreferenceManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest =
            chain.request().newBuilder().apply {
                addHeader("Host", "i.instagram.com")
                addHeader("Cookie", prefsManager.loginCookies)
                addHeader("accept", "*/*")
                addHeader("cookie2", "\$Version=1")
                addHeader("accept-language", "en-US")
                addHeader("user-agent",  "Instagram 22.0.0.15.68 Android (23/6.0.1; 320dpi; 720x1280; OPPO; CPH1701; CPH1701; qualcommtechnologies,incmsm8940; en_US)")
            }.build()
        return chain.proceed(newRequest)

    }

}