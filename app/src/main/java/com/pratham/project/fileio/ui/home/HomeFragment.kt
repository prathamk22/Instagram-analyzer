package com.pratham.project.fileio.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.local.models.HashtagsCountModel
import com.pratham.project.fileio.data.local.models.LocationCountModel
import com.pratham.project.fileio.databinding.FragmentHomeBinding
import com.pratham.project.fileio.databinding.HashtagsItemBinding
import com.pratham.project.fileio.databinding.LocationItemBinding
import com.pratham.project.fileio.utils.GenericAdapter
import com.pratham.project.fileio.utils.base.BaseFragment
import com.pratham.project.fileio.utils.observeList
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val vm: HomeViewModel by stateViewModel()
    private val hashtagsAdapter = object : GenericAdapter<HashtagsCountModel, HashtagsItemBinding>(R.layout.hashtags_item){
        override fun onBind(item: HashtagsCountModel, adapterItemBinding: HashtagsItemBinding) {

        }
    }

    private val locationsAdapter = object : GenericAdapter<LocationCountModel, LocationItemBinding>(R.layout.location_item){
        override fun onBind(item: LocationCountModel, adapterItemBinding: LocationItemBinding) {

        }
    }

    override fun setBindings(binding: FragmentHomeBinding) {
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hashTagsRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = hashtagsAdapter
        }
        binding.locationRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = locationsAdapter
        }

        hashtagsAdapter.observeList(vm.hashtagsListLD, viewLifecycleOwner)
        locationsAdapter.observeList(vm.locationListLD, viewLifecycleOwner)

    }
}
