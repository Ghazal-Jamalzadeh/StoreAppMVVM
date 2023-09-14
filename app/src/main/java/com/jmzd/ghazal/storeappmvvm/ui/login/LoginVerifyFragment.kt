package com.jmzd.ghazal.storeappmvvm.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding

class LoginVerifyFragment : Fragment() {

    //binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginVerifyBinding.inflate(layoutInflater)
        return binding.root    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}