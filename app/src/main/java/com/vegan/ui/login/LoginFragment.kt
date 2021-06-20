package com.vegan.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vegan.MainActivity
import com.vegan.R
import com.vegan.data.UserRepository
import com.vegan.databinding.FragmentLoginBinding
import com.vegan.extentions.toast
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.loginProgress.progress, it.loginContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSmsCodePart(false)
        observeData()
        stateController.showContent()
        binding.loginButton.setOnClickListener {
            viewModel.sendSmsCode(
                binding.loginPhone.text.toString(),
                requireActivity() as AppCompatActivity
            )
        }
        binding.loginSendSmsCode.setOnClickListener {
            viewModel.validateSmsCode(binding.loginSmsCode.text.toString())
        }

        binding.loginButtonToRegistration.setOnClickListener {
            findNavController().navigate(R.id.open_registration)
        }

    }

    private fun observeData() {
        viewModel.screenState.observe(viewLifecycleOwner, { state ->
            when (state) {
                UserRepository.UserAuthState.SMS_CODE_NOT_SENT -> stateController.showContent()
                UserRepository.UserAuthState.SMS_START_CODE_SENT -> stateController.showProgress()
                UserRepository.UserAuthState.SMS_END_CODE_SENT -> {
                    showSmsCodePart(true)
                    stateController.showContent()
                }
                UserRepository.UserAuthState.SMS_CODE_VERIFICATION -> stateController.showProgress()
                UserRepository.UserAuthState.REGISTERED -> {
                    toast("Успешно зарегистрировались")
                    startActivity(MainActivity.newIntent(requireContext()))
                }
                UserRepository.UserAuthState.USER_NOT_REGISTERED_YET -> toast("Зарегистрируйся")
                UserRepository.UserAuthState.LOGGED_IN -> {
                    toast("Успешно вошли")
                    startActivity(MainActivity.newIntent(requireContext()))
                }
            }
        })
    }

    private fun showSmsCodePart(isVisible: Boolean) {
        binding.loginSendSmsCode.isVisible = isVisible
        binding.loginSmsCodeLayout.isVisible = isVisible

        binding.loginButton.isVisible = !isVisible
        binding.loginPhoneLayout.isVisible = !isVisible
        binding.loginButtonToRegistration.isVisible = !isVisible
    }
}