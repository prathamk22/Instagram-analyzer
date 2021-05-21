package com.pratham.project.fileio.utils.base

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.pratham.project.fileio.R
import com.pratham.project.fileio.utils.LOGIN_URI

abstract class BaseFragment<DataBinding : ViewDataBinding>(private val layoutRes: Int) : Fragment(){

    protected lateinit var binding: DataBinding
    protected var TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.large_animation).toLong()
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.large_animation).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.large_animation).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        setBindings(binding)
        return binding.root
    }

    fun navigateToLoginView(){
        try {
            view?.findNavController()?.navigate(Uri.parse(LOGIN_URI))
        }catch (e: Exception){

        }
    }

    protected abstract fun setBindings(binding: DataBinding)

}