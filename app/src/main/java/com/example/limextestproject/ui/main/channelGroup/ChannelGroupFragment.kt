package com.example.limextestproject.ui.main.channelGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.limextestproject.databinding.FragmentChannelGroupBinding
import com.example.limextestproject.ui.common.onDestroyNullable
import com.example.limextestproject.ui.common.viewModelCreator
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter.Companion.ChannelGroups
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

        private const val CHANNEL_GROUP = "CHANNEL_TITLE"
    }

    @Inject
    lateinit var assistedFactory: ChannelGroupViewModelAssistedFactory
    private val viewModel: ChannelGroupViewModel by viewModelCreator {
        assistedFactory.create(
            (requireArguments().getParcelable(CHANNEL_GROUP) as? ChannelGroups)!!
        )
    }
    private var binding by onDestroyNullable<FragmentChannelGroupBinding>()

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
        binding.title.text = viewModel.currentChannelGroup.title
    }
}
