package com.pratham.project.fileio.ui.logins

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.utils.INSTAGRAM_URL
import com.pratham.project.fileio.utils.base.BaseFragment
import com.pratham.project.fileio.databinding.LoginFragmentBinding
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<LoginFragmentBinding>(R.layout.login_fragment) {

    private val prefsManager: PreferenceManager by inject()

    var loaded = 0

    override fun setBindings(binding: LoginFragmentBinding) {
        binding.prefs = prefsManager
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                val cookies = CookieManager.getInstance().getCookie(url)
                Log.e(TAG, "onPageFinished: $url : $cookies")
                if (url?.equals("https://www.instagram.com/", true) == true) {
                    loaded++
                    if (loaded == 2) {
                        prefsManager.loginCookies = cookies
                        getView()?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                }
            }
        }
        if(prefsManager.loginCookies.isNullOrEmpty()){
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(INSTAGRAM_URL)
        }
    }

}