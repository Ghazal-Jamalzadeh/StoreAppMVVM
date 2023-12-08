package com.jmzd.ghazal.storeappmvvm.ui.categories_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesProductsBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesProductFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesProductsBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //args
    private val args by navArgs<CategoriesProductFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            Toast.makeText(requireContext(), "slug is ${it.slug}", Toast.LENGTH_SHORT).show()
        }

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