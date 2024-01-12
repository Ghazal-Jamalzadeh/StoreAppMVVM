package com.jmzd.ghazal.storeappmvvm.utils.di

import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySubmitAddress
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun bodyLogin() = BodyLogin()

    @Provides
    fun bodyUpdateProfile() = BodyUpdateProfile()

    @Provides
    fun bodyIncreaseWallet() = BodyIncreaseWallet()

    @Provides
    fun bodySubmitAddress() = BodySubmitAddress()
}