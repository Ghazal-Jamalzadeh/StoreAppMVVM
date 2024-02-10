package com.jmzd.ghazal.storeappmvvm.ui

import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.stored.SessionManager
import com.jmzd.ghazal.storeappmvvm.databinding.ActivityMainBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseActivity
import com.jmzd.ghazal.storeappmvvm.utils.events.EventBus
import com.jmzd.ghazal.storeappmvvm.utils.events.Events
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.InternetConnectionChecker
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.otp.AppSignatureHelper
import com.jmzd.ghazal.storeappmvvm.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    //binding
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    //nav controller
    private lateinit var navHost : NavHostFragment

    //viewModel
    private val cartViewModel by viewModels<CartViewModel>()

    @Inject
    lateinit var signatureHelper: AppSignatureHelper

    //connectivity
    @Inject
    lateinit var networkChecker: InternetConnectionChecker
    @Inject
    lateinit var sessionManager: SessionManager
    private var isNetworkAvailable = true

    //other
    var hashCode : String = ""

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init nav host
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        //bottom nav
        binding.bottomNav.apply {
            //attach nav host to bottom nav
            setupWithNavController(navController = navHost.navController)
            //disable double click on items (to avoid recreate fragments)
            /*دپریکیت شده چون میگه جدیدا بیاید دستی هندل کنید کلیک ها رو. ولی این روش کارآمدتریه در کل*/
            setOnNavigationItemReselectedListener {}
        }

        //gone bottom menu
        navHost.navController.addOnDestinationChangedListener { controller, destination, arguments ->
           binding.apply {
               when(destination.id){
                   R.id.splashFragment ,
                   R.id.loginPhoneFragment ,
                   R.id.loginVerifyFragment ,
                       R.id.detailFragment ,
                   -> bottomNav.isVisible = false
                   else -> bottomNav.isVisible = true
               }
           }
        }

        //Generate Hashcode
        signatureHelper.appSignatures.forEach { value : String ->
            hashCode = value
            Log.e("HashcodeLogs", "Hashcode : $hashCode")
        }

        //Check network
        lifecycleScope.launch {
            networkChecker.checkConnectivity().collect {
                isNetworkAvailable = it
            }
        }

        //update badge
        lifecycleScope.launch {
            EventBus.subscribe<Events.IsUpdateCart> {
                cartViewModel.getCartList()
            }
        }

        //check if user has token then get cart list and show cart badge if needed in first load
        lifecycleScope.launch {
            delay(200)
            sessionManager.getToken.collect { token ->
                token?.let {
                    if (isNetworkAvailable) {
                        cartViewModel.getCartList()
                    }
                }
            }
        }

        //observers
        observeBadgeLiveData()

    }

    //--- observers ---//

    private fun observeBadgeLiveData() {
        binding.apply {
            cartViewModel.cartListLiveData.observe(this@MainActivity) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                    }

                    is MyResponse.Success -> {
                        response.data?.let { data ->
                            if (data.quantity != null) {
                                if (data.quantity.toString().toInt() > 0) {
                                    bottomNav.getOrCreateBadge(R.id.cartFragment).apply {
                                        number = data.quantity.toString().toInt()
                                        backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.caribbeanGreen)
                                    }
                                } else {
                                    bottomNav.removeBadge(R.id.cartFragment)
                                }
                            } else {
                                bottomNav.removeBadge(R.id.cartFragment)
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}