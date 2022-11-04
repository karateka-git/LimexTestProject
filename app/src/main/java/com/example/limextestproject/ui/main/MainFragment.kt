package com.example.limextestproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.limextestproject.databinding.FragmentMainBinding
import com.example.limextestproject.databinding.TabViewBinding
import com.example.limextestproject.ui.common.onDestroyNullable
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter.Companion.ChannelGroups
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding by onDestroyNullable<FragmentMainBinding>()
    private val channelGroupsAdapter by lazy {
        ChannelGroupViewPagerAdapter(childFragmentManager, lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.channels.observe(viewLifecycleOwner) {
            // TODO()
        }
        binding.apply {
            channelGroupViewPager.adapter = channelGroupsAdapter
            TabLayoutMediator(channelGroupTabLayout, channelGroupViewPager) { tab, position ->
                tab.customView = TabViewBinding.inflate(layoutInflater).apply {
                    tabTitle.text = ChannelGroups.values()[position].title
                }.root
            }.attach()
        }
    }
}
