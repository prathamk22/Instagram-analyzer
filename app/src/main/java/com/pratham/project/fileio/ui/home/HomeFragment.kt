package com.pratham.project.fileio.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.flexbox.*
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.local.models.HashtagsCountModel
import com.pratham.project.fileio.data.local.models.LocationCountModel
import com.pratham.project.fileio.databinding.FragmentHomeBinding
import com.pratham.project.fileio.databinding.HashtagsItemBinding
import com.pratham.project.fileio.databinding.LocationItemBinding
import com.pratham.project.fileio.utils.GenericAdapter
import com.pratham.project.fileio.utils.LocationsMarginDecoration
import com.pratham.project.fileio.utils.base.BaseFragment
import com.pratham.project.fileio.utils.observeList
import com.pratham.project.fileio.utils.toDp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels()
    private val hashtagsAdapter = object : GenericAdapter<HashtagsCountModel, HashtagsItemBinding>(R.layout.hashtags_item){
        override fun onBind(item: HashtagsCountModel, adapterItemBinding: HashtagsItemBinding) {
            adapterItemBinding.hashtagText.text = item.hashtag
        }
    }

    private val locationsAdapter = object : GenericAdapter<LocationCountModel, LocationItemBinding>(R.layout.location_item){
        override fun onBind(item: LocationCountModel, adapterItemBinding: LocationItemBinding) {
            val radius = 15f.toDp(requireContext().resources.displayMetrics)
            adapterItemBinding.locationText.setRadius(floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius))
            adapterItemBinding.locationText.text = item.location?.shortName
            adapterItemBinding.postCount.text = "${item.count}\nPosts"
        }
    }

    override fun setBindings(binding: FragmentHomeBinding) {
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getUsernameDetails()
        binding.hashTagsRv.apply {
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP).apply {
                alignItems = AlignItems.STRETCH
                justifyContent = JustifyContent.FLEX_START
            }
            adapter = hashtagsAdapter
        }
        binding.locationRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = locationsAdapter
            addItemDecoration(LocationsMarginDecoration())
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.locationRv)

        hashtagsAdapter.observeList(vm.hashtagsListLD, viewLifecycleOwner)
        locationsAdapter.observeList(vm.locationListLD, viewLifecycleOwner)

    }
}
