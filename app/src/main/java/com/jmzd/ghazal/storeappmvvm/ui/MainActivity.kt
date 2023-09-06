package com.jmzd.ghazal.storeappmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //binding
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    //nav controller
    private lateinit var navHost : NavHostFragment


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
                   R.id.splashFragment -> bottomNav.isVisible = false
                   else -> bottomNav.isVisible = true
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