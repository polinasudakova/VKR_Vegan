package com.vegan.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vegan.databinding.FragmentMapBinding
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMapBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.mapProgress.progress, it.mapContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateController.showContent()
        binding.mapMap.onCreate(savedInstanceState)
        binding.mapMap.getMapAsync {
            println()
        }
        observeData()
    }

    private fun observeData() {
        viewModel.screenProgress.observe(viewLifecycleOwner) {
            if (it) stateController.showProgress() else stateController.showContent()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapMap.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.mapMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapMap.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapMap.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapMap.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        binding.mapMap.onResume()
    }

}