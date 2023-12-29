package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepository  @Inject constructor(private val api : ApiServices) {
    suspend fun getProfile() = api.getProfile()
    suspend fun uploadAvatar(body : RequestBody) = api.uploadAvatar(body)
    suspend fun updateProfie(body : BodyUpdateProfile) = api.updateProfile(body)
}