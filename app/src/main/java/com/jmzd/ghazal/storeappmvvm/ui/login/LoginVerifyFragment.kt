package com.jmzd.ghazal.storeappmvvm.ui.login

import academy.nouri.storeapp.utils.otp.SMSBroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginVerifyFragment : Fragment() {

    //binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

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

        //init views
        binding.apply {

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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