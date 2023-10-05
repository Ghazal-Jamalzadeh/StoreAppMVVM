package com.jmzd.ghazal.storeappmvvm.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSearchBinding
import com.jmzd.ghazal.storeappmvvm.ui.search.adapters.SearchAdapter
import com.jmzd.ghazal.storeappmvvm.utils.NEW
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showKeyboard
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    //binding
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<SearchViewModel>()

    //adapter
    @Inject lateinit var searchAdapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Toolbar
            toolbar.apply {
                //Back
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
                //Title
                toolbarTitleTxt.text = getString(R.string.searchInProducts)
                //Option
                toolbarOptionImg.isVisible = false
            }

            //Auto open keyboard
            lifecycleScope.launch {
                delay(300)
                searchEdt.showKeyboard(requireActivity())
            }

            //Search
            searchEdt.addTextChangedListener {
                if (it.toString().length > 3) {
                    if (isNetworkAvailable) {
                        viewModel.search(viewModel.getSearchQueries(it.toString(), NEW))
                    }
                }
                //Empty
                if (it.toString().isEmpty()) {
                    emptyLay.isVisible = true
                    searchList.isVisible = false
                }
            }
        }

        //observers
        observeSearchLiveData()
    }

    //--- observers ---//
    private fun observeSearchLiveData() {
        binding.apply {
            viewModel.searchLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        searchList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        searchList.hideShimmer()
                        response.data?.let { data ->
                            data.products?.let { products ->
                                if (products.data?.isNotEmpty()!!) {
                                    emptyLay.isVisible = false
                                    searchList.isVisible = true
                                    //Init recycler
                                    initSearchRecycler(products.data)
                                } else {
                                    emptyLay.isVisible = true
                                    searchList.isVisible = false
                                }
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        searchList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- Recyclers ---//
    private fun initSearchRecycler(data: List<ResponseSearch.Products.Data>) {
        searchAdapter.setData(data)
        binding.searchList.setupRecyclerview(LinearLayoutManager(requireContext()), searchAdapter)
        //Click
        searchAdapter.setOnItemClickListener {

        }
    }

    override fun onNetworkLost() {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}