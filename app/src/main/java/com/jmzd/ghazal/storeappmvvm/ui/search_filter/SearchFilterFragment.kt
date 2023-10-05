package com.jmzd.ghazal.storeappmvvm.ui.search_filter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSearchFilterBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : BaseFragment() {

    //binding
    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    //viewModel
//    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater)
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