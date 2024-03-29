package com.jmzd.ghazal.storeappmvvm.ui.login

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.goodiebag.pinview.Pinview
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseVerify
import com.jmzd.ghazal.storeappmvvm.data.stored.SessionManager
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.utils.constants.IS_CALLED_VERIFY
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.hideKeyboard
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.otp.SMSBroadcastReceiver
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginVerifyFragment : BaseFragment() {

    //binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //args
    private val args by navArgs<LoginVerifyFragmentArgs>()

    @Inject
    lateinit var body: BodyLogin

    @Inject
    lateinit var sessionManager: SessionManager

    //sms receiver
    @Inject
    lateinit var smsBroadcastReceiver: SMSBroadcastReceiver
    private var intentFilter: IntentFilter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginVerifyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //flags
        IS_CALLED_VERIFY = false

        //sms receiver
        initBroadCast()
        smsReceiver()

        //animation
        handleAnimation()

        //args
        args.let {
            body.login = it.mobile
        }

        //timer
        lifecycleScope.launch {
            delay(500)
            handleTimer().start()
        }

        //observers
        observeLoginLiveData()
        observeVerifyLiveData()

        //init views
        binding.apply {

            //Bottom image
            bottomImg.load(R.drawable.bg_circle)

            //Customize pin view text color
            pinView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            //Complete code
            pinView.setPinViewEventListener(object : Pinview.PinViewEventListener {
                override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                    body.code = pinview?.value?.toInt()
                    if (isNetworkAvailable)
                        viewModel.verify(body)
                }
            })

            //resend code
            sendAgainBtn.setOnClickListener {
                if (isNetworkAvailable)
                    viewModel.login(body)
                handleTimer().cancel()
            }

        }

    }

    //--- observers ---//
    private fun observeLoginLiveData() {
        binding.apply {
            viewModel.loginLiveData.observe(viewLifecycleOwner) { response: NetworkRequest<ResponseLogin>? ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        sendAgainBtn.enableLoading(true)
                    }
                    is NetworkRequest.Error -> {
                        sendAgainBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                    is NetworkRequest.Success -> {
                        sendAgainBtn.enableLoading(false)
                        response.data.let {
                            handleTimer().start()
                        }
                    }
                }
            }
        }
    }

    private fun observeVerifyLiveData() {
        binding.apply {
            viewModel.verifyLiveData.observe(viewLifecycleOwner) { response: NetworkRequest<ResponseVerify>? ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        timerLay.alpha = 0.3f
                    }
                    is NetworkRequest.Error -> {
                        timerLay.alpha = 1.0f

                        root.showSnackBar(response.message!!)
                    }
                    is NetworkRequest.Success -> {
                        timerLay.alpha = 1.0f

                        response.data.let { data :ResponseVerify?  ->
                            lifecycleScope.launch{
                                sessionManager.saveToken(data!!.accessToken.toString())
                            }
                            root.hideKeyboard()
                            findNavController().popBackStack(R.id.loginVerifyFragment , true)
                            findNavController().popBackStack(R.id.loginPhoneFragment , true)
                            findNavController().navigate(R.id.action_to_nav_home)

                        }
                    }
                }
            }
        }
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

    //--- Timer ---//
    private fun handleTimer(): CountDownTimer {
        binding.apply {
            return object : CountDownTimer(60_000, 1_000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millis: Long) {
                    sendAgainBtn.isVisible = false
                    timerTxt.isVisible = true
                    timerProgress.isVisible = true
                    timerTxt.text = "${millis / 1000} ${getString(R.string.second)}"
                    timerProgress.setProgressCompat((millis / 1000).toInt(), true)
                    if (millis < 1000)
                        timerProgress.progress = 0
                }

                override fun onFinish() {
                    sendAgainBtn.isVisible = true
                    timerTxt.isVisible = false
                    timerProgress.isVisible = false
                    timerProgress.progress = 0
                }
            }
        }
    }

    //--- SMS receiver ---//
    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsBroadcastReceiver.onReceiveMessage { sms: String ->
            Log.e("SMSLogs", "SMS : $sms")
            /*
            سلام.
            کد ورود : 3204
            نوری آکادمی shop.nouri-api.ir
            mngx1fgshu+
                    */
            val code = sms.split(":")[1].trim().subSequence(0, 4)
            binding.pinView.value = code.toString()
        }
    }

    private fun smsReceiver() {
        val client = SmsRetriever.getClient(requireActivity())
        client.startSmsRetriever()
    }

    //--- life cycle ---//
    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(smsBroadcastReceiver)
        handleTimer().cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNetworkLost() {
        Log.d("onNetworkLost", "onNetworkLost: yoyo ")
    }

}
