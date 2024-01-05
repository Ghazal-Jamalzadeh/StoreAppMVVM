package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import javax.inject.Inject

class ProfileFavoritesRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getFavorites() = api.getFavorites()
    suspend fun deleteFavorite(id : Int) = api.deleteFavorite(id)
}