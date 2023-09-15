package com.jmzd.ghazal.storeappmvvm.ui.login

import academy.nouri.storeapp.utils.otp.SMSBroadcastReceiver
import android.animation.Animator
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginVerifyFragment : Fragment() {

    //binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //args
    private val args by navArgs<LoginVerifyFragmentArgs>()

    @Inject
    lateinit var body: BodyLogin

    //sms receiver
    @Inject
    lateinit var smsBroadcastReceiver : SMSBroadcastReceiver
    private var intentFilter : IntentFilter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginVerifyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //sms receiver
        initBroadCast()
        smsReceiver()

        //animation
        handleAnimation()

        //args
        args.let {
            body.login = it.mobile
        }

        //init views
        binding.apply {

            //Bottom image
            bottomImg.load(R.drawable.bg_circle)


        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //--- animation ---//
    private fun handleAnimation() {

        //لوپ انیمین رو خودمون فالس کردیم چون تکرار سریعش آزار دهنده هس
        // در عوض میایم به صورت دستی میگیم:
        // بعد از هر با پلی دو ثانیه صبر کن بعد دوباره پلی کن

        binding.animationView.apply {
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    lifecycleScope.launch {
                        delay(2000)
                        playAnimation() // متدهای انیمیشن ویوی لاتی هست
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    //--- SMS receiver ---//
    private fun initBroadCast(){
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsBroadcastReceiver.onReceiveMessage { sms : String ->
            Log.e("SMSLogs", "SMS : $sms")
        }
    }

    private fun smsReceiver(){
        val client = SmsRetriever.getClient(requireActivity())
        client.startSmsRetriever()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(smsBroadcastReceiver , intentFilter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(smsBroadcastReceiver)
    }



}