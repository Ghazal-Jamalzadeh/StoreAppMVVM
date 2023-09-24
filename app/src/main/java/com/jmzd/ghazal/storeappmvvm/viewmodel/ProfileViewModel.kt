package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.data.repository.PofileRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: PofileRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(300)
            getProfileData()
        }
    }

    //profile
    private val _profileLiveData = MutableLiveData<MyResponse<ResponseProfile>>()
    val profileLiveData: LiveData<MyResponse<ResponseProfile>> = _profileLiveData

    private fun getProfileData() = viewModelScope.launch {

        _profileLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfile> = repository.getProfile()

        _profileLiveData.value = ResponseHandler(response).generateResponse()
    }
}