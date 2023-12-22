package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseWallet
import com.jmzd.ghazal.storeappmvvm.data.repository.WalletRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val repository: WalletRepository) : ViewModel() {

    //banners
    private val _walletBalanceLiveData = MutableLiveData<MyResponse<ResponseWallet>>()
    val walletBalanceLiveData: LiveData<MyResponse<ResponseWallet>> = _walletBalanceLiveData

    //--- api call ---//
    private fun getWalletBalance() = viewModelScope.launch {

        _walletBalanceLiveData.value = MyResponse.Loading()

        val response: Response<ResponseWallet> = repository.getWalletBalance()

        _walletBalanceLiveData.value = ResponseHandler(response).generateResponse()
    }



}