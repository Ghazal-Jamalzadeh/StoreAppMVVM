package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProductLike
import com.jmzd.ghazal.storeappmvvm.data.repository.DetailRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    //detail
    private val _detailLiveData = MutableLiveData<MyResponse<ResponseDetail>>()
    val detailLiveData: LiveData<MyResponse<ResponseDetail>> = _detailLiveData
    //like post
    private val _likeLiveData = MutableLiveData<MyResponse<ResponseProductLike>>()
    val likeLiveData: LiveData<MyResponse<ResponseProductLike>> = _likeLiveData


    //--- api call ---//
    fun getDetail(productId : Int) = viewModelScope.launch {

        _detailLiveData.value = MyResponse.Loading()

        val response: Response<ResponseDetail> = repository.getDetail(productId)

        _detailLiveData.value = ResponseHandler(response).generateResponse()
    }

    //--- api call ---//
    fun likeProduct(productId : Int) = viewModelScope.launch {

        _likeLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProductLike> = repository.postLikeProduct(productId)

        _likeLiveData.value = ResponseHandler(response).generateResponse()
    }





}