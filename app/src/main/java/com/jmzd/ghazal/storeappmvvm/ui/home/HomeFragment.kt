package com.jmzd.ghazal.storeappmvvm.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentHomeBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.ui.login.LoginPhoneFragmentDirections
import com.jmzd.ghazal.storeappmvvm.utils.IS_CALLED_VERIFY
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.loadImage
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {

        }
        //observers
        observeProfileLiveData()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //--- observers ---//
    private fun observeProfileLiveData() {
        binding.apply {
            profileViewModel.profileLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseProfile>? ->
                when (response) {
                    is MyResponse.Loading -> {
                        avatarLoading.isVisible = true
                    }
                    is MyResponse.Error -> {
                        avatarLoading.isVisible = false

                        root.showSnackBar(response.message!!)
                    }
                    is MyResponse.Success -> {
                        avatarLoading.isVisible = false

                        response.data?.let {
                            //Avatar
                            if (it.avatar != null) {
                                avatarImg.loadImage(it.avatar)
                                avatarBadgeImg.isVisible = false
                            } else {
                                avatarImg.load(R.drawable.placeholder_user)
                                avatarBadgeImg.isVisible = true
                            }
                        }
                    }

                }
            }
        }
    }


}