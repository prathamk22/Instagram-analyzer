package com.pratham.project.fileio.ui.logins

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.findNavController
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.databinding.LoginFragmentBinding
import com.pratham.project.fileio.utils.INSTAGRAM_URL
import com.pratham.project.fileio.utils.base.BaseFragment
import okhttp3.internal.userAgent
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<LoginFragmentBinding>(R.layout.login_fragment) {

    private val prefsManager: PreferenceManager by inject()

    var loaded = 0

    override fun setBindings(binding: LoginFragmentBinding) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookies = CookieManager.getInstance().getCookie(url)
                prefsManager.loginCookies = cookies
                prefsManager.userAgent = view?.settings?.userAgentString ?: ""
                Log.e(TAG, "onPageFinished: $url : $cookies")
                if (url?.equals("https://www.instagram.com/", true) == true){
                    loaded++
                    if (loaded == 2){
                        getView()?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                }
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(INSTAGRAM_URL)
    }

}