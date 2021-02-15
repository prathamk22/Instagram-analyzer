package com.pratham.project.fileio.ui

import androidx.navigation.fragment.navArgs
import com.pratham.project.fileio.R
import com.pratham.project.fileio.databinding.FragmentErrorBinding
import com.pratham.project.fileio.utils.base.BaseBottomSheetDialogFragment

class ErrorFragment : BaseBottomSheetDialogFragment<FragmentErrorBinding>(R.layout.fragment_error) {

    private val navArgs: ErrorFragmentArgs by navArgs()
    private lateinit var title: String
    private lateinit var subTitle: String

    override fun setBindings(binding: FragmentErrorBinding) {
        binding.title = title
        binding.subTitle = subTitle
    }

    override fun getArgs() {
        title = navArgs.title
        subTitle = navArgs.subTitle
    }

}
