package com.jmzd.ghazal.storeappmvvm.ui.categories_filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesFiltersBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFiltersFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesFiltersBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesFiltersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {

        }
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}