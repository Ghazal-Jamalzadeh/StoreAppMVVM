package com.jmzd.ghazal.storeappmvvm.utils.network

sealed class NetworkRequest<T>(val data : T? = null , val message : String? = null  ) {
    class Loading<T> : NetworkRequest<T>()
    class Success<T>(data : T) : NetworkRequest<T>(data = data)
    class Error<T>(message : String) : NetworkRequest<T>(message = message)
}