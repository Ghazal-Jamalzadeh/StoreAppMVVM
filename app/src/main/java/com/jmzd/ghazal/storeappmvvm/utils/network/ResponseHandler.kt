package com.jmzd.ghazal.storeappmvvm.utils.network

import com.google.gson.Gson
import com.jmzd.ghazal.storeappmvvm.data.models.ErrorResponse
import retrofit2.Response

open class ResponseHandler<T> (private val response: Response<T>) {
    open fun generateResponse() : MyResponse<T>{
        return when {
            response.code() == 401 -> MyResponse.Error("You are not authorized")
            response.code() == 422 -> {
                var errorMessage = ""
                if (response.errorBody() != null) {
                    val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                    val message : String? = errorResponse.message
                    val errors : Map<String , List<String>>?  = errorResponse.errors
                    errors?.forEach { (key : String, value: List<String>) ->
                        errorMessage = value.joinToString()
                    }
                }
                MyResponse.Error(errorMessage)
            }

            response.code() == 500 -> MyResponse.Error("Try again")
            response.isSuccessful -> MyResponse.Success(response.body()!!)
            else -> MyResponse.Error(response.message())
        }
    }
}