package com.jmzd.ghazal.storeappmvvm.ui.profile_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileEditBinding
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileViewModel>()

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call api
        viewModel.getProfileData()

        //Init views
        binding.apply {
            //Close
            closeImg.setOnClickListener { this@ProfileEditFragment.dismiss() }
        }

        //observers
        observeProfileLiveData()
    }


    @SuppressLint("SetTextI18n")
    private fun observeProfileLiveData() {
        binding.apply {
            viewModel.profileLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        loading.isVisible = true
                    }

                    is MyResponse.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            if (data.firstname.isNullOrEmpty().not())
                                nameEdt.setText(data.firstname)
                            if (data.lastname.isNullOrEmpty().not())
                                familyEdt.setText(data.lastname)
                            if (data.idNumber.isNullOrEmpty().not())
                                idNumberEdt.setText(data.idNumber)
                            if (data.birthDate.isNullOrEmpty().not())
                                birthDateEdt.setText(data.birthDate!!.split("T")[0])
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}