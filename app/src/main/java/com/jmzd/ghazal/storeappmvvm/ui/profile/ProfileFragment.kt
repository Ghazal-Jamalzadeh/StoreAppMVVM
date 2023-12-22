package com.jmzd.ghazal.storeappmvvm.ui.profile

import android.annotation.SuppressLint
import android.net.NetworkRequest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileBinding
import com.jmzd.ghazal.storeappmvvm.utils.extensions.changeVisibility
import com.jmzd.ghazal.storeappmvvm.utils.extensions.loadImage
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    //binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
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

    //--- observers ---//

    @SuppressLint("SetTextI18n")
    private fun observeProfileLiveData() {
        binding.apply {
            viewModel.profileLiveData.observe(viewLifecycleOwner) { response : MyResponse<ResponseProfile>->
                when (response) {
                    is MyResponse.Loading -> {
                        loading.isVisible = true
                    }

                    is MyResponse.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            //Avatar
                            if (data.avatar != null) {
                                avatarImg.loadImage(data.avatar)
                            } else {
                                avatarImg.load(R.drawable.placeholder_user)
                            }
                            //Name
                            if (data.firstname.isNullOrEmpty().not())
                                nameTxt.text = "${data.firstname} ${data.lastname}"
                            //Info
                            infoLay.apply {
                                phoneTxt.text = data.cellphone
                                //Birthdate
                                if (data.birthDate!!.isNotEmpty()) {
                                    birthDateTxt.text = data.birthDate.split("T")[0]
                                        .replace("-", " / ")
                                } else {
                                    infoBirthDateLay.isVisible = false
                                    line2.isVisible = false
                                }
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- life cycle ---//
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}