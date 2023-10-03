package com.jmzd.ghazal.storeappmvvm.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginVerifyFragment : Fragment() {

    //binding
    private var _binding: FragmentLoginVerifyBinding? = null
    private val binding get() = _binding!!

    //viewModel
//    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginVerifyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}