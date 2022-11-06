package com.example.limextestproject.ui.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.limextestproject.databinding.FragmentPlayerBinding
import com.example.limextestproject.ui.common.onDestroyNullable
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var binding by onDestroyNullable<FragmentPlayerBinding>()
    private val args by navArgs<PlayerFragmentArgs>()
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(requireContext()).build()
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
        initOnBackPressedCallback()
        initExoPlayer()
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
            it.setMediaItem(MediaItem.fromUri("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"))
            it.prepare()
            it.play()
        }
    }

    private fun initOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                findNavController().navigateUp()
            }
    }
}
