package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseWallet
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.ResponseIncreaseWallet
import com.jmzd.ghazal.storeappmvvm.data.repository.WalletRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val repository: WalletRepository) : ViewModel() {

    //wallet balance
    private val _walletBalanceLiveData = MutableLiveData<NetworkRequest<ResponseWallet>>()
    val walletBalanceLiveData: LiveData<NetworkRequest<ResponseWallet>> = _walletBalanceLiveData

    //increase wallet
    private val _increaseWalletLiveData = MutableLiveData<NetworkRequest<ResponseIncreaseWallet>>()
    val increaseWalletLiveData: LiveData<NetworkRequest<ResponseIncreaseWallet>> = _increaseWalletLiveData

    //--- api call ---//
    fun getWalletBalance() = viewModelScope.launch {

        _walletBalanceLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseWallet> = repository.getWalletBalance()

        _walletBalanceLiveData.value = ResponseHandler(response).generateResponse()
    }

    //--- increase wallet ---//
    fun increaseWallet(body : BodyIncreaseWallet) = viewModelScope.launch {

        _increaseWalletLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseIncreaseWallet> = repository.increaseWallet(body)

        _increaseWalletLiveData.value = ResponseHandler(response).generateResponse()
    }


}