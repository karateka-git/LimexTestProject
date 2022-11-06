package com.example.limextestproject.ui.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.limextestproject.R
import com.example.limextestproject.databinding.FragmentPlayerBinding
import com.example.limextestproject.ui.common.onDestroyNullable
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var binding by onDestroyNullable<FragmentPlayerBinding>()
    private val args by navArgs<PlayerFragmentArgs>()
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(requireContext())
            .build()
    }
    private val qualityPopUp: PopupMenu by lazy {
        PopupMenu(requireContext(), exoQuality)
    }

    private val exoQuality: ImageView by lazy {
        binding.root.findViewById(R.id.exo_quality)
    }
    private val exoBackButton: ImageView by lazy {
        binding.root.findViewById(R.id.exo_back_button)
    }
    private val exoChannelImage: ImageView by lazy {
        binding.root.findViewById(R.id.exo_channel_image)
    }
    private val exoProgramTitle: TextView by lazy {
        binding.root.findViewById(R.id.exo_program_title)
    }
    private val exoChannelTitle: TextView by lazy {
        binding.root.findViewById(R.id.exo_channel_title)
    }
    private val exoTimeUntilEnd: TextView by lazy {
        binding.root.findViewById(R.id.exo_time_until_end)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideSystemBars()
        initOnBackPressedCallback()
        initExoPlayer()
        initPlayerControls()
        initListeners()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    // "https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"
    // args.url
    private fun initExoPlayer() {
        binding.exoplayer.player = exoPlayer.also {
            it.setMediaItem(MediaItem.fromUri("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"))
            it.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        updateQualityPopupMenu(
                            generateQualityList(exoPlayer.currentTracks.groups)
                        )
                        exoPlayer.currentPosition
                    }
                }
            })
            it.prepare()
            it.play()
        }
    }

    private fun videoProgress(exoPlayer: ExoPlayer) = flow {
        while (true) {
            emit(exoPlayer.currentPosition.toFloat())
            delay(1000)
        }
    }.flowOn(Dispatchers.Main)

    private fun initPlayerControls() {
        val channel = args.channel
        val programDurationInMinutes = (channel.currentProgram.timestop - channel.currentProgram.timestart).div(60)
        Glide.with(requireContext())
            .load(channel.image)
            .into(exoChannelImage)
        exoProgramTitle.text = channel.currentProgram.title
        exoChannelTitle.text = channel.name
        lifecycleScope.launch(Dispatchers.IO) {
            videoProgress(exoPlayer).collect {
                exoTimeUntilEnd.text = getString(R.string.time_until_end, (programDurationInMinutes - it.div(60000).toInt()))
            }
        }
    }

    private fun generateQualityList(groups: ImmutableList<Tracks.Group>): MutableList<Quality> {
        val qualityList: MutableList<Quality> = mutableListOf()
        groups.find {
            it.length != 0 && it.type == C.TRACK_TYPE_VIDEO
        }?.let { videoGroup ->
            for (i in 0 until videoGroup.length) {
                if (videoGroup.isTrackSupported(i)) {
                    val track = videoGroup.getTrackFormat(i)
                    val trackName = "${track.width}p"
                    val trackSelectionOverride = TrackSelectionOverride(
                        videoGroup.mediaTrackGroup,
                        i
                    )
                    val trackIsSelected = videoGroup.isTrackSelected(i)
                    qualityList.add(Quality(trackName, trackSelectionOverride, trackIsSelected))
                }
            }
            val autoQuality = if (qualityList.all { it.isSelected }) {
                qualityList.forEach { it.isSelected = false }
                true
            } else {
                false
            }
            qualityList.add(
                Quality(
                    "Auto",
                    TrackSelectionOverride(
                        videoGroup.mediaTrackGroup,
                        (0 until videoGroup.length).toList()
                    ),
                    autoQuality
                )
            )
        }

        return qualityList
    }

    private fun updateQualityPopupMenu(qualityList: MutableList<Quality>) {
        qualityPopUp.menu.clear()
        qualityList.forEachIndexed { index, (name, _, isSelected) ->
            qualityPopUp.menu.add(0, index, 0, name)
        }
        qualityPopUp.setOnMenuItemClickListener { menuItem ->
            qualityList[menuItem.itemId].let { (_, selectionOverride, _) ->
                exoPlayer.trackSelectionParameters =
                    exoPlayer.trackSelectionParameters
                        .buildUpon()
                        .setOverrideForType(selectionOverride)
                        .build()
            }
            true
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun initOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                exit()
            }
    }

    private fun initListeners() {
        exoQuality.setOnClickListener {
            qualityPopUp.show()
        }
        exoBackButton.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        findNavController().navigateUp()
    }
}

private data class Quality(
    val name: String,
    val selectionOverride: TrackSelectionOverride,
    var isSelected: Boolean = false
)
