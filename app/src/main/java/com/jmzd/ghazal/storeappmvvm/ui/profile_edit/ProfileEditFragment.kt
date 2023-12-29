package com.jmzd.ghazal.storeappmvvm.ui.profile_edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileEditBinding
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileViewModel>()

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

    //body
    @Inject
    lateinit var body : BodyUpdateProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileEditBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call api
        viewModel.getProfileData()

        //Init views
        binding.apply {
            //Close
            closeImg.setOnClickListener { this@ProfileEditFragment.dismiss() }

            //Open date picker
            birthDateEdt.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    openDatePicker()
                }
                false
            }

            //Submit data
            submitBtn.setOnClickListener {
                if (nameEdt.text.isNullOrEmpty().not())
                    body.firstName = nameEdt.text.toString()
                if (familyEdt.text.isNullOrEmpty().not())
                    body.lastName = familyEdt.text.toString()
                if (idNumberEdt.text.isNullOrEmpty().not())
                    body.idNumber = idNumberEdt.text.toString()
                //Call api
                viewModel.updateProfile(body)
            }
        }

        //observers
        observeProfileLiveData()
        observeUpdateProfileLiveData()
    }

    //--- observers ---//
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

    private fun observeUpdateProfileLiveData() {
        binding.apply {
            viewModel.updateProfileLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        submitBtn.enableLoading(true)
                    }

                    is MyResponse.Success -> {
                        submitBtn.enableLoading(false)
                        response.data?.let {
//                            lifecycleScope.launch {
//                                EventBus.publish(Events.IsUpdateProfile)
//                            }
                            this@ProfileEditFragment.dismiss()
                        }
                    }

                    is MyResponse.Error -> {
                        submitBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- date picker ---//
    private fun openDatePicker() {
        PersianDatePickerDialog(requireContext())
            .setTodayButtonVisible(true)
            .setMinYear(1300)
            .setMaxYear(1400)
            .setInitDate(1370, 3, 13)
            .setTitleType(PersianDatePickerDialog.DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(pDate: PersianPickerDate) {
                    val birthDate = "${pDate.gregorianYear}-${pDate.gregorianMonth}-${pDate.gregorianDay}"
                    val birthDatePersian = "${pDate.persianYear}-${pDate.persianMonth}-${pDate.persianDay}"
                    body.gregorianDate = birthDate
                    binding.birthDateEdt.setText(birthDatePersian)
                }

                override fun onDismissed() {}
            }).show()
    }

    //--- life cycle ---//
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}