package com.jmzd.ghazal.storeappmvvm.ui.categories_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesProductsBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.CategoryProductsViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesProductFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesProductsBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<CategoryProductsViewModel>()

    //args
    private val args by navArgs<CategoriesProductFragmentArgs>()
    private var slug: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //init views
        binding.apply {

            //get args
            args.let {
                slug = it.slug
            }

            //Toolbar
            toolbar.apply {
                //Back
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
                //Title
//                toolbarTitleTxt.text = getString(R.string.searchInProducts)
                //Option
                toolbarOptionImg.setOnClickListener{
                    findNavController().navigate(CategoriesProductFragmentDirections.actionCategoriesProductsFragmentToCategoriesFiltersFragment())
                }
            }

            //call api
            if(isNetworkAvailable){
                viewModel.getProductsByCategory(slug , viewModel.getProductsQueries())
            }

            //observers
            observeProductsLiveData()

        }
    }

    //--- observers ---//
    private fun observeProductsLiveData() {
        binding.apply {
            viewModel.productsLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        productsList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        productsList.hideShimmer()
                        response.data?.let { data ->
                            data.subCategory?.products?.let { products ->
                                if (!products.data.isNullOrEmpty()) {
                                    emptyLay.isVisible = false
                                    productsList.isVisible = true
                                    //Init recycler
                                    initProductsRecycler(products.data)
                                } else {
                                    emptyLay.isVisible = true
                                    productsList.isVisible = false
                                }
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        productsList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- Recyclers ---//
    private fun initProductsRecycler(data: List<ResponseProducts.SubCategory.Products.Data>) {
//        searchAdapter.setData(data)
//        binding.productsList.setupRecyclerview(LinearLayoutManager(requireContext()), searchAdapter)
//        //Click
//        searchAdapter.setOnItemClickListener {
//
//        }
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}