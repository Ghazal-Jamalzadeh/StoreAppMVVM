package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseDeleteComment
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseProfileComments
import com.jmzd.ghazal.storeappmvvm.data.repository.ProfileCommentsRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileCommentsViewModel @Inject constructor(private val repository: ProfileCommentsRepository) : ViewModel() {

    //profile comments
    private val _profileCommentsLiveData = MutableLiveData<NetworkRequest<ResponseProfileComments>>()
    val profileCommentsLiveData: LiveData<NetworkRequest<ResponseProfileComments>> = _profileCommentsLiveData

    //delete comment
    private val _deleteCommentLiveData = MutableLiveData<NetworkRequest<ResponseDeleteComment>>()
    val deleteCommentLiveData: LiveData<NetworkRequest<ResponseDeleteComment>> = _deleteCommentLiveData

    //--- api call ---//
    fun getProfileComments() = viewModelScope.launch {

        _profileCommentsLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProfileComments> = repository.getProfileComments()

        _profileCommentsLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun deleteComment(commentId : Int) = viewModelScope.launch {

        _deleteCommentLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseDeleteComment> = repository.deleteCommnt(commentId)

        _deleteCommentLiveData.value = ResponseHandler(response).generateResponse()
    }



}