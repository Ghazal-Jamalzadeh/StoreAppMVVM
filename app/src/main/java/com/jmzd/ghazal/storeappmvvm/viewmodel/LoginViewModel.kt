package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.repository.LoginRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    //login
    private val _loginLiveData = MutableLiveData<MyResponse<ResponseLogin>>()
    val loginLiveData : LiveData<MyResponse<ResponseLogin>> = _loginLiveData

    fun login(body: BodyLogin) = viewModelScope.launch {

        _loginLiveData.value = MyResponse.Loading()

        val response : Response<ResponseLogin> =  repository.postLogin(body)

        _loginLiveData.value = ResponseHandler(response).generateResponse()
    }
}