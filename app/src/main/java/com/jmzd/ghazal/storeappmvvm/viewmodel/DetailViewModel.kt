package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.comments.ResponseCommentsList
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures
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

    //product id
    private val _productIdLiveData = MutableLiveData<Int>()
    val productIdLiveData: LiveData<Int> = _productIdLiveData
    //detail
    private val _detailLiveData = MutableLiveData<MyResponse<ResponseDetail>>()
    val detailLiveData: LiveData<MyResponse<ResponseDetail>> = _detailLiveData
    //like post
    private val _likeLiveData = MutableLiveData<MyResponse<ResponseProductLike>>()
    val likeLiveData: LiveData<MyResponse<ResponseProductLike>> = _likeLiveData
    //features
    private val _featuresLiveData = MutableLiveData<MyResponse<ResponseProductFeatures>>()
    val featuresLiveData: LiveData<MyResponse<ResponseProductFeatures>> = _featuresLiveData
    //comments
    private val _commentsLiveData = MutableLiveData<MyResponse<ResponseCommentsList>>()
    val commentsLiveData: LiveData<MyResponse<ResponseCommentsList>> = _commentsLiveData


    //--- getter & setter ---//
    fun setProductId(id: Int) {
        _productIdLiveData.value = id
    }

    //--- api call ---//
    fun getDetail(productId : Int) = viewModelScope.launch {

        _detailLiveData.value = MyResponse.Loading()

        val response: Response<ResponseDetail> = repository.getDetail(productId)

        _detailLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun likeProduct(productId : Int) = viewModelScope.launch {

        _likeLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProductLike> = repository.postLikeProduct(productId)

        _likeLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getFeatures(productId : Int) = viewModelScope.launch {

        _featuresLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProductFeatures> = repository.getDetailFeatures(productId)

        _featuresLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun getComments(productId : Int) = viewModelScope.launch {

        _commentsLiveData.value = MyResponse.Loading()

        val response: Response<ResponseCommentsList> = repository.getDetailComments(productId)

        _commentsLiveData.value = ResponseHandler(response).generateResponse()
    }




}