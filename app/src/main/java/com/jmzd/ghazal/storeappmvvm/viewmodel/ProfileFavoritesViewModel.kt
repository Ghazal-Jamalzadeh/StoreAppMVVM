package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseDeleteComment
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProfileFavorites
import com.jmzd.ghazal.storeappmvvm.data.repository.ProfileFavoritesRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileFavoritesViewModel @Inject constructor(private val repository: ProfileFavoritesRepository) : ViewModel() {

    //profile favorites
    private val _profileFavoritesLiveData = MutableLiveData<NetworkRequest<ResponseProfileFavorites>>()
    val profileFavoritesLiveData: LiveData<NetworkRequest<ResponseProfileFavorites>> = _profileFavoritesLiveData

    //delete favorite
    private val _deleteFavoriteLiveData = MutableLiveData<NetworkRequest<ResponseDeleteComment>>()
    val deleteFavoriteLiveData: LiveData<NetworkRequest<ResponseDeleteComment>> = _deleteFavoriteLiveData

    //--- api call ---//
    fun getFavorites() = viewModelScope.launch {

        _profileFavoritesLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProfileFavorites> = repository.getFavorites()

        _profileFavoritesLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun deleteFavorite(id : Int) = viewModelScope.launch {

        _deleteFavoriteLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseDeleteComment> = repository.deleteFavorite(id)

        _deleteFavoriteLiveData.value = ResponseHandler(response).generateResponse()
    }



}