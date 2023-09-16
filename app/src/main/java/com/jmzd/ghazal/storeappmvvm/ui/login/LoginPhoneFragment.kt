package com.jmzd.ghazal.storeappmvvm.ui.login

import android.animation.Animator
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.ui.MainActivity
import com.jmzd.ghazal.storeappmvvm.utils.IS_CALLED_VERIFY
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.hideKeyboard
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginPhoneFragment : BaseFragment() {

    //binding
    private var _binding: FragmentLoginPhoneBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //activity
    private val parentActivity by lazy {
        (activity as MainActivity)
    }

    @Inject
    lateinit var body: BodyLogin

    //other
    private var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginPhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Bottom image
            bottomImg.load(R.drawable.bg_circle)

            //animation
            handleAnimation()

            //hash code
            body.hashCode = parentActivity.hashCode

            //--- observers ---//
            observeLoginLiveData()

            //--- clicks ---//
            binding.sendPhoneBtn.setOnClickListener {
                //hide keyboard
                root.hideKeyboard()

                //call api
                phone = phoneEdt.text.toString()
                if (phone.length == 11) {
                    body.login = phone
                    if (isNetworkAvailable) {
                        IS_CALLED_VERIFY = true
                        viewModel.login(body)
                    }
                } else {
                    root.showSnackBar("تعداد ارقام موبایل وارد شده نادرست است")
                }
            }

        }
    }

    override fun onNetworkLost() {
        Snackbar.make(requireView(), "network lost :))))))", Snackbar.LENGTH_SHORT).show()
    }

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

    private fun observeLoginLiveData() {
        binding.apply {
            viewModel.loginLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseLogin>? ->
                when (response) {
                    is MyResponse.Loading -> {
                        sendPhoneBtn.enableLoading(true)
                    }
                    is MyResponse.Error -> {
                        sendPhoneBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                    is MyResponse.Success -> {
                        sendPhoneBtn.enableLoading(false)
                        response.data.let {
                            if (IS_CALLED_VERIFY) {
                                val direction =
                                    LoginPhoneFragmentDirections.actionLoginPhoneFragmentToLoginVerifyFragment()
                                        .setMobile(phone)
                                findNavController().navigate(direction)
//                            findNavController().navigate(R.id.action_loginPhoneFragment_to_loginVerifyFragment)
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}