package com.jmzd.ghazal.storeappmvvm.utils.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@Suppress("DEPRECATION")
class InternetConnectionChecker @Inject constructor(private val manager: ConnectivityManager, private val request: NetworkRequest) :
    ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)
    private var capabilities: NetworkCapabilities? = null

    fun checkConnectivity(): MutableStateFlow<Boolean> {
        //Register
        manager.registerNetworkCallback(request, this)
        //Init network
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Active network
            /* کلا شبکه فعالی هس یا نه */
            val activeNetwork = manager.activeNetwork
            if (activeNetwork == null) {
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }
            //Capabilities
            /* در غیر این صورت بیا ببین به چه نتورکی وصله */
            capabilities = manager.getNetworkCapabilities(activeNetwork)
            if (capabilities == null) {
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }

            isNetworkAvailable.value = when {
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            manager.run {
                manager.activeNetworkInfo?.run {
                    isNetworkAvailable.value = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}