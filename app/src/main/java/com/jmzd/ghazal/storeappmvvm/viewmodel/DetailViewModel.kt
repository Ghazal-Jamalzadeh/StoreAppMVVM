package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.SimpleResponse
import com.jmzd.ghazal.storeappmvvm.data.models.comments.BodySendComment
import com.jmzd.ghazal.storeappmvvm.data.models.comments.ResponseCommentsList
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductPriceChart
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProductLike
import com.jmzd.ghazal.storeappmvvm.data.repository.DetailRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
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
    private val _detailLiveData = MutableLiveData<NetworkRequest<ResponseDetail>>()
    val detailLiveData: LiveData<NetworkRequest<ResponseDetail>> = _detailLiveData
    //like post
    private val _likeLiveData = MutableLiveData<NetworkRequest<ResponseProductLike>>()
    val likeLiveData: LiveData<NetworkRequest<ResponseProductLike>> = _likeLiveData
    //features
    private val _featuresLiveData = MutableLiveData<NetworkRequest<ResponseProductFeatures>>()
    val featuresLiveData: LiveData<NetworkRequest<ResponseProductFeatures>> = _featuresLiveData
    //comments
    private val _commentsLiveData = MutableLiveData<NetworkRequest<ResponseCommentsList>>()
    val commentsLiveData: LiveData<NetworkRequest<ResponseCommentsList>> = _commentsLiveData
    //send comment
    private val _sendCommentLiveData = MutableLiveData<NetworkRequest<SimpleResponse>>()
    val sendCommentLiveData: LiveData<NetworkRequest<SimpleResponse>> = _sendCommentLiveData
    //price chart
    private val _priceChartLiveData = MutableLiveData<NetworkRequest<ResponseProductPriceChart>>()
    val priceChartLiveData: LiveData<NetworkRequest<ResponseProductPriceChart>> = _priceChartLiveData

    //--- getter & setter ---//
    fun setProductId(id: Int) {
        _productIdLiveData.value = id
    }

    //--- api call ---//
    fun getDetail(productId : Int) = viewModelScope.launch {

        _detailLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseDetail> = repository.getDetail(productId)

        _detailLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun likeProduct(productId : Int) = viewModelScope.launch {

        _likeLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProductLike> = repository.postLikeProduct(productId)

        _likeLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getFeatures(productId : Int) = viewModelScope.launch {

        _featuresLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProductFeatures> = repository.getDetailFeatures(productId)

        _featuresLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun getComments(productId : Int) = viewModelScope.launch {

        _commentsLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseCommentsList> = repository.getDetailComments(productId)

        _commentsLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun sendComment(productId : Int , body : BodySendComment) = viewModelScope.launch {

        _sendCommentLiveData.value = NetworkRequest.Loading()

        val response: Response<SimpleResponse> = repository.sendComment(productId , body)

        _sendCommentLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getPriceChart(productId : Int) = viewModelScope.launch {

        _priceChartLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProductPriceChart> = repository.getPriceChart(productId)

        _priceChartLiveData.value = ResponseHandler(response).generateResponse()
    }




}