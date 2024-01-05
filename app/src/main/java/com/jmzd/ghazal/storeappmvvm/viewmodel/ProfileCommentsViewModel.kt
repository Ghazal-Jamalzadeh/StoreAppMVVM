package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.categories.ResponseCategories
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseWallet
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseProfileComments
import com.jmzd.ghazal.storeappmvvm.data.repository.ProfileCommentsRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileCommentsViewModel @Inject constructor(private val repository: ProfileCommentsRepository) : ViewModel() {

    //profile comments
    private val _profileCommentsLiveData = MutableLiveData<MyResponse<ResponseProfileComments>>()
    val profileCommentsLiveData: LiveData<MyResponse<ResponseProfileComments>> = _profileCommentsLiveData

    //--- api call ---//
    fun getProfileComments() = viewModelScope.launch {

        _profileCommentsLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfileComments> = repository.getProfileComments()

        _profileCommentsLiveData.value = ResponseHandler(response).generateResponse()
    }

}