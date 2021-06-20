package com.vegan.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vegan.MainActivity
import com.vegan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.view_fullscreen_progressbar) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.screenState.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(MainActivity.newIntent(requireContext()))
            } else {
                findNavController().navigate(R.id.open_login)
            }
        }
    }

}