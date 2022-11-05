package com.example.limextestproject.ui.main.channelGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.limextestproject.databinding.FragmentChannelGroupBinding
import com.example.limextestproject.ui.common.onDestroyNullable
import com.example.limextestproject.ui.common.viewModelCreator
import com.example.limextestproject.ui.main.MainViewModel
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter.Companion.ChannelGroups
import com.example.limextestproject.ui.main.channelGroup.adapters.ChannelsAdapter
import com.example.limextestproject.ui.main.channelGroup.adapters.ChannelsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChannelGroupFragment : Fragment() {

    companion object {
        fun newInstance(channelGroup: ChannelGroups) = ChannelGroupFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CHANNEL_GROUP, channelGroup)
            }
        }

        private const val CHANNEL_GROUP = "CHANNEL_GROUP_KEY"
    }

    @Inject
    lateinit var assistedFactory: ChannelGroupViewModelAssistedFactory
    private val viewModel: ChannelGroupViewModel by viewModelCreator {
        assistedFactory.create(
            (requireArguments().getParcelable(CHANNEL_GROUP) as? ChannelGroups)!!
        )
    }
    private val sharedViewModel: MainViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var binding by onDestroyNullable<FragmentChannelGroupBinding>()

    private val channelAdapter by lazy {
        ChannelsAdapter {
            ChannelsViewHolder(
                it,
                viewModel::addChannelToFavorite
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChannelGroupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        binding.apply {
            channelsRecycler.adapter = channelAdapter
        }
    }

    private fun initObservers() {
        viewModel.channels.observe(viewLifecycleOwner) {
            channelAdapter.swapItems(it)
        }
        sharedViewModel.searchQuery.observe(viewLifecycleOwner) {
            viewModel.filterChannels(it)
        }
    }
}
