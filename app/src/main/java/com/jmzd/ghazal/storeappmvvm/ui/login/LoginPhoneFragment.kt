package com.jmzd.ghazal.storeappmvvm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.ui.MainActivity
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginPhoneFragment : Fragment() {

    //binding
    private var _binding : FragmentLoginPhoneBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //activity
    private val parentActivity by lazy {
        (activity as MainActivity)
    }

    @Inject
    private lateinit var body : BodyLogin

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

            //hash code
            body.hashCode = parentActivity.hashCode

            //--- clicks ---//
            binding.sendPhoneBtn.setOnClickListener{

                phone = phoneEdt.text.toString()
                if (phone.length == 11){
                    body.login = phone
                    viewModel.login(body)
                }else{
                    Snackbar.make(requireView() , "تعداد ارقام موبایل وارد شده نادرست است" , Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}