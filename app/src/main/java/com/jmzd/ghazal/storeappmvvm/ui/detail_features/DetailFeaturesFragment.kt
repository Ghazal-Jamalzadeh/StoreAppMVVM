package com.jmzd.ghazal.storeappmvvm.ui.detail_features

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailFeaturesBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.changeVisibility
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFeaturesFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailFeaturesBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<DetailViewModel>()

    //adapter
    @Inject
    lateinit var featuresAdapter: FeaturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailFeaturesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //Get id
        viewModel.productIdLiveData.observe(viewLifecycleOwner) {
            if (isNetworkAvailable)
                Log.d("tagTest", "features api called with $it ")
                viewModel.getFeatures(it)
        }

        //init views
        binding.apply {

        }

        //observers
        observeFeaturesLiveData()
    }


    private fun observeFeaturesLiveData() {
        binding.apply {
            viewModel.featuresLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        featuresLoading.changeVisibility(true, featuresList)
                    }

                    is MyResponse.Success -> {
                        featuresLoading.changeVisibility(false, featuresList)
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                initRecycler(data)
                                emptyLay.isVisible = false
                            } else {
                                emptyLay.isVisible = true
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        featuresLoading.changeVisibility(false, featuresList)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initRecycler(data: List<ResponseProductFeatures.ResponseProductFeaturesItem>) {
        featuresAdapter.setData(data)
        binding.featuresList.setupRecyclerview(LinearLayoutManager(requireContext()), featuresAdapter)
    }


    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}