package com.jmzd.ghazal.storeappmvvm.utils.network

sealed class MyResponse<T>(val data : T? = null, val message : String? = null  ) {
    class Loading<T> : MyResponse<T>()
    class Success<T>(data : T) : MyResponse<T>(data = data)
    class Error<T>(message : String) : MyResponse<T>(message = message)
}