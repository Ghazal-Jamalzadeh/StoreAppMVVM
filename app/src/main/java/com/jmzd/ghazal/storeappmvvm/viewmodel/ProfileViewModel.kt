package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.data.repository.ProfileRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(300)
            getProfileData()
        }
    }

    //profile
    private val _profileLiveData = MutableLiveData<MyResponse<ResponseProfile>>()
    val profileLiveData: LiveData<MyResponse<ResponseProfile>> = _profileLiveData
    //upload avatar
    private val _avatarLiveData = MutableLiveData<MyResponse<Unit>>()
    val avatarLiveData: LiveData<MyResponse<Unit>> = _avatarLiveData
    //update profile
    private val _updateProfileLiveData = MutableLiveData<MyResponse<ResponseProfile>>()
    val updateProfileLiveData: LiveData<MyResponse<ResponseProfile>> = _updateProfileLiveData

    //--- call api ---//
    fun getProfileData() = viewModelScope.launch {

        _profileLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfile> = repository.getProfile()

        _profileLiveData.value = ResponseHandler(response).generateResponse()
    }

     fun uploadAvatar(body : RequestBody) = viewModelScope.launch {

        _avatarLiveData.value = MyResponse.Loading()

        val response: Response<Unit> = repository.uploadAvatar(body)

        _avatarLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun updateProfile(body : BodyUpdateProfile) = viewModelScope.launch {

        _updateProfileLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfile> = repository.updateProfie(body)

        _updateProfileLiveData.value = ResponseHandler(response).generateResponse()
    }

}