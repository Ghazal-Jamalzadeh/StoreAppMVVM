package com.jmzd.ghazal.storeappmvvm.ui.detail_features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailFeaturesBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFeaturesFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailFeaturesBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<DetailViewModel>()

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
                viewModel.getFeatures(it)
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