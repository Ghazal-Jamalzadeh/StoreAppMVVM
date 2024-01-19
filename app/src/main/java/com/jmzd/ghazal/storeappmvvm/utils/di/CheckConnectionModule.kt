package com.jmzd.ghazal.storeappmvvm.utils.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.jmzd.ghazal.storeappmvvm.utils.constants.NAMED_VPN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckConnectionModule {

    //--- Connectivity Manager ---//
    @Provides
    @Singleton
    fun provideCM(@ApplicationContext context : Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //--- Network Request ---//
    // check connection NR
    @Provides
    @Singleton
    fun provideNR(): NetworkRequest = NetworkRequest.Builder().apply {
        addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        //Android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        //Android P
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            /*تو اندروید های بالا حتما باید به صورت فورگراند باشه سرویس ها*/
            addCapability(NetworkCapabilities.NET_CAPABILITY_FOREGROUND)
    }.build()

    //vpn NR
    @Provides
    @Singleton
    @Named(NAMED_VPN)
    fun provideNrVpn(): NetworkRequest = NetworkRequest.Builder().apply {
        addTransportType(NetworkCapabilities.TRANSPORT_VPN)
        removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
    }.build()

    //--- VPN Checker ---// متونست به شکل کلاس هم باشه مثل اینترنت کانکشن چکر
    @Provides
    @Singleton
    fun provideCheckVpn(manager: ConnectivityManager, @Named(NAMED_VPN) request: NetworkRequest):
            Flow<Boolean> = callbackFlow {

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                channel.trySend(false)
            }
        }

        manager.registerNetworkCallback(request, callback)

        awaitClose {
            manager.unregisterNetworkCallback(callback)
        }
    }

}