package com.jmzd.ghazal.storeappmvvm.utils.di

import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun bodyLogin() = BodyLogin()
}