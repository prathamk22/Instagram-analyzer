package com.pratham.project.fileio.ui.logins

import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.navigation.findNavController
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.utils.INSTAGRAM_LOGIN_URL
import com.pratham.project.fileio.utils.base.BaseFragment
import com.pratham.project.fileio.databinding.LoginFragmentBinding
import com.pratham.project.fileio.utils.INSTAGRAM_URL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(R.layout.login_fragment) {

    @Inject lateinit var prefsManager: PreferenceManager

    var loaded = 0

    override fun setBindings(binding: LoginFragmentBinding) {
        binding.prefs = prefsManager
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookies = CookieManager.getInstance().getCookie(url)
                if (url?.equals(INSTAGRAM_URL, true) == true) {
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
            binding.webView.loadUrl(INSTAGRAM_LOGIN_URL)
        }

        binding.userCard.setOnClickListener {
            getView()?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }

}