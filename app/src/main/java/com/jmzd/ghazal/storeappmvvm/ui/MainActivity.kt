package com.jmzd.ghazal.storeappmvvm.ui

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.ActivityMainBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseActivity
import com.jmzd.ghazal.storeappmvvm.utils.otp.AppSignatureHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    //binding
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    //nav controller
    private lateinit var navHost : NavHostFragment

    @Inject
    lateinit var signatureHelper: AppSignatureHelper

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
                   R.id.loginVerifyFragment
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

    }




    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}