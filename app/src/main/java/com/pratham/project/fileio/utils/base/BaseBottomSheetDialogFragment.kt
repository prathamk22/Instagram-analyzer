package com.pratham.project.fileio.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pratham.project.fileio.R

abstract class BaseBottomSheetDialogFragment<DataBinding : ViewDataBinding>(private val layoutRes: Int): BottomSheetDialogFragment() {

    protected lateinit var binding: DataBinding
    protected var TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme)
        getArgs()
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

    protected abstract fun setBindings(binding: DataBinding)

    abstract fun getArgs()
}
