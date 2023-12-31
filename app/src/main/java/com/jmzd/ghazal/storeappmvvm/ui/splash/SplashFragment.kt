package com.jmzd.ghazal.storeappmvvm.ui.splash

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmzd.ghazal.storeappmvvm.BuildConfig
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.stored.SessionManager
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    //binding
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Version Name
            versionTxt.text = "${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"
        }

        checkSession()
    }

    private fun checkSession() {
        binding.motionLay.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float,
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                //check user
                lifecycleScope.launch {
                    val token: String? = sessionManager.getToken.first()
                    Log.d(TAG, "token: $token")
                    findNavController().popBackStack(R.id.splashFragment, true)
                    if (token.isNullOrEmpty()) {
                        findNavController().navigate(R.id.action_to_nav_login)
                    } else {
                        //main
                        findNavController().navigate(R.id.action_to_nav_home)

                    }
                }

            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float,
            ) {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}