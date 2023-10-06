package com.jmzd.ghazal.storeappmvvm.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.categories.ResponseCategories
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCartBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.CategoriesViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<CategoriesViewModel>()

    //adapter
    //TODO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Toolbar
            toolbar.apply {
                //Visibility
                toolbarBackImg.isVisible = false
                toolbarOptionImg.isVisible = false
                //Title
                toolbarTitleTxt.text = getString(R.string.categories)
            }
        }

        //observers
        observeCategoriesLiveData()
    }


    private fun observeCategoriesLiveData() {
        binding.apply {
            viewModel.categoriesLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        categoriesList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        categoriesList.hideShimmer()
                        response.data?.let { data ->
                            initCategoriesRecycler(data)
                        }
                    }

                    is MyResponse.Error -> {
                        categoriesList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initCategoriesRecycler(data: List<ResponseCategories.ResponseCategoriesItem>) {
//        categoryAdapter.setData(data.dropLast(1))
//        binding.categoriesList.setupRecyclerview(LinearLayoutManager(requireContext()), categoryAdapter)
//        //Click
//        categoryAdapter.setOnItemClickListener {
//            val direction = CategoriesFragmentDirections.actionToCategoriesProduct(it)
//            findNavController().navigate(direction)
//        }
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}