package com.vegan.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.vegan.AuthActivity
import com.vegan.R
import com.vegan.databinding.FragmentProfileBinding
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.profileProgress.progress, it.profileContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateController.showContent()
        observeData()
    }

    private fun observeData() {
        viewModel.screenState.observe(viewLifecycleOwner, { user ->
            if (user == null) {
                startActivity(AuthActivity.newIntent(requireContext()))
            } else
                with(user) {
                    binding.apply {
                        profileAvatar.load(user.avatarUrl)
                        profileName.text = "$firstName $secondName"
                        profilePhone.text = phone

                        profileToolbar.setOnMenuItemClickListener {
                            when (it.itemId) {
                                R.id.profile_favorite -> findNavController().navigate(R.id.toFavorites)
                                R.id.profile_logout -> viewModel.onSignoutClick()
                            }
                            true
                        }
                        profileRefrigeratorLayout.setOnClickListener {
                            findNavController().navigate(R.id.toRefrigerator)
                        }

                        profileStopListLayout.setOnClickListener {
                            findNavController().navigate(R.id.toStopList)
                        }
                    }
                }
        })
    }

}