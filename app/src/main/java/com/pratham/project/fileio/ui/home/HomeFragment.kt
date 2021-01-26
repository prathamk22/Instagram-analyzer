package com.pratham.project.fileio.ui.home

import android.os.Bundle
import android.view.View
import com.pratham.project.fileio.R
import com.pratham.project.fileio.databinding.FragmentHomeBinding
import com.pratham.project.fileio.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val vm: HomeViewModel by stateViewModel()

    override fun setBindings(binding: FragmentHomeBinding) {
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
