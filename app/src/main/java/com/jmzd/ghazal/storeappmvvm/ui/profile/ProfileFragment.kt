package com.jmzd.ghazal.storeappmvvm.ui.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.app.imagepickerlibrary.ImagePicker
import com.app.imagepickerlibrary.ImagePicker.Companion.registerImagePicker
import com.app.imagepickerlibrary.listener.ImagePickerResultListener
import com.app.imagepickerlibrary.model.PickExtension
import com.app.imagepickerlibrary.model.PickerType
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseWallet
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileBinding
import com.jmzd.ghazal.storeappmvvm.utils.*
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URLEncoder

@AndroidEntryPoint
class ProfileFragment : BaseFragment(), ImagePickerResultListener {

    //binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val walletViewModel by viewModels<WalletViewModel>()

    //image picker
    private val imagePicker: ImagePicker by lazy { registerImagePicker(this) }

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
            //Choose image
            avatarEditImg.setOnClickListener {
                openImagePicker()
            }

            //menu items
            menuLay.apply {

                //edit profile
                menuEditLay.setOnClickListener {
                    findNavController().navigate(R.id.action_to_edit_profile_fragment)
                }
            }
        }
        //observers
        observeProfileLiveData()
        observeWalletBalanceLiveData()
        observeAvatarLiveData()
    }

    //--- observers ---//
    @SuppressLint("SetTextI18n")
    private fun observeProfileLiveData() {
        binding.apply {
            viewModel.profileLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseProfile> ->
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

    private fun observeWalletBalanceLiveData() {
        binding.infoLay.apply {
            walletViewModel.walletBalanceLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseWallet> ->
                when (response) {
                    is MyResponse.Loading -> {
                        walletLoading.changeVisibility(true, walletTxt)
                    }

                    is MyResponse.Success -> {
                        walletLoading.changeVisibility(false, walletTxt)
                        response.data?.let { data: ResponseWallet ->
                            walletTxt.text = data.wallet.toString().toInt().moneySeparating()
                        }
                    }

                    is MyResponse.Error -> {
                        walletLoading.changeVisibility(false, walletTxt)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun observeAvatarLiveData() {
        binding.apply {
            viewModel.avatarLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        avatarLoading.isVisible = true
                    }

                    is MyResponse.Success -> {
                        avatarLoading.isVisible = false
                        if (isNetworkAvailable)
                            viewModel.getProfileData()
                    }

                    is MyResponse.Error -> {
                        avatarLoading.isVisible = false
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    //--- image picker ---//
    private fun openImagePicker() {
        imagePicker
            .title(getString(R.string.galleryImages))
            .multipleSelection(false)
            .showFolder(true)
            .cameraIcon(true)
            .doneIcon(true)
            .allowCropping(true)
            .compressImage(false)
            .maxImageSize(2.5f)//mb
            .extension(PickExtension.ALL)// jpg - png - webp (یک فرمت تصویری هست که توسط گوگل ارایه شده و پرفورمنس خیلی خوبی داره)
        imagePicker.open(PickerType.GALLERY)
    }

    override fun onImagePick(uri: Uri?) {

        val imageFile = getRealFileFromUri(requireContext(), uri!!)?.let { path -> File(path) }

        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(METHOD, POST)

        //Image body
        if (imageFile != null) {
            val fileName = URLEncoder.encode(imageFile.absolutePath, UTF_8)
            val reqFile = imageFile.asRequestBody(MULTIPART_FROM_DATA.toMediaTypeOrNull())
            multipart.addFormDataPart(AVATAR, fileName, reqFile)
        }
        //Call api
        val requestBody = multipart.build()

        if (isNetworkAvailable) {
            viewModel.uploadAvatar(requestBody)
        }

    }

    override fun onMultiImagePick(uris: List<Uri>?) {
    }

    //--- life cycle ---//
    override fun onNetworkLost() {
    }

    override fun onResume() {
        super.onResume()
        walletViewModel.getWalletBalance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}