package com.pratham.project.fileio.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.pratham.project.fileio.R
import com.pratham.project.fileio.databinding.FragmentDashboardBinding
import com.pratham.project.fileio.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    override fun setBindings(binding: FragmentDashboardBinding) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.bottomNav.setupWithNavController(Navigation.findNavController(requireActivity(), R.id.dashboardNav))
    }
}