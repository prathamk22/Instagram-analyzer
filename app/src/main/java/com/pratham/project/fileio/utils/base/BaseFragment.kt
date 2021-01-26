package com.pratham.project.fileio.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DataBinding : ViewDataBinding>(private val layoutRes: Int) : Fragment(){

    protected lateinit var binding: DataBinding
    protected var TAG: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        setBindings(binding)
        return binding.root
    }

    protected abstract fun setBindings(binding: DataBinding)

}