package com.vegan.ui.registration

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.vegan.MainActivity
import com.vegan.R
import com.vegan.data.UserRepository
import com.vegan.databinding.FragmentRegistrationBinding
import com.vegan.extentions.toast
import com.vegan.ui.view.ScreenStateController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding
    private lateinit var stateController: ScreenStateController

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegistrationBinding.inflate(inflater)
        .also {
            binding = it
            stateController =
                ScreenStateController(it.registrationProgress.progress, it.registrationContent)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSmsCodePart(false)
        observeData()
        stateController.showContent()
        binding.registrationButton.setOnClickListener {
            viewModel.sendSmsCode(
                binding.registrationPhone.text.toString(),
                binding.registrationFirstName.text.toString(),
                binding.registrationSecondName.text.toString(),
                requireActivity() as AppCompatActivity
            )
        }
        binding.registrationSendSmsCode.setOnClickListener {
            viewModel.validateSmsCode(binding.registrationSmsCode.text.toString())
        }

        binding.registrationButtonToLogin.setOnClickListener {
            findNavController().navigate(R.id.open_login)
        }

        binding.registrationAvatar.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            data?.data
                                ?.also(viewModel::onPhotoPicked)
                                ?.also(binding.registrationAvatar::setImageURI)
                                ?: toast("Пустой data")
                        }
                        ImagePicker.RESULT_ERROR -> toast(ImagePicker.getError(data))
                        else -> toast("Ошибка выбора фотографии")
                    }
                }
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
                    toast("Успешно зарегались")
                    startActivity(MainActivity.newIntent(requireContext()))
                }
                UserRepository.UserAuthState.USER_NOT_REGISTERED_YET -> toast("Зарегистрируйся")
            }
        })
    }

    private fun showSmsCodePart(isVisible: Boolean) {
        binding.registrationSendSmsCode.isVisible = isVisible
        binding.registrationSmsCodeLayout.isVisible = isVisible

        binding.registrationAvatar.isVisible = !isVisible
        binding.registrationButton.isVisible = !isVisible
        binding.registrationFirstNameLayout.isVisible = !isVisible
        binding.registrationSecondNameLayout.isVisible = !isVisible
        binding.registrationPhoneLayout.isVisible = !isVisible
        binding.registrationButtonToLogin.isVisible = !isVisible
    }
}