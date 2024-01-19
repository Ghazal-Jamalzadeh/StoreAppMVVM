package com.jmzd.ghazal.storeappmvvm.ui.profile_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile_order.ResponseProfileOrdersList
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileOrdersBinding
import com.jmzd.ghazal.storeappmvvm.ui.profile_orders.adapters.OrdersAdapter
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.constants.CANCELED
import com.jmzd.ghazal.storeappmvvm.utils.constants.DELIVERED
import com.jmzd.ghazal.storeappmvvm.utils.constants.PENDING
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileOrdersFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileOrdersBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileOrdersViewModel>()

    //args
    private val args by navArgs<ProfileOrdersFragmentArgs>()

    //adapter
    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    //other
    private var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //args
        args.status.let {
            status = it
        }

        //call api
        if (isNetworkAvailable) viewModel.getOrdersList(status)

        //init views
        binding.apply {
            toolbar.apply {
                toolbarTitleTxt.text = when (status) {
                    DELIVERED -> getString(R.string.delivered)
                    PENDING -> getString(R.string.pending)
                    CANCELED -> getString(R.string.canceled)
                    else -> ""
                }
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
                toolbarOptionImg.isVisible = false
            }
        }

        //observers
        observeOrdersLiveData()
    }

    //--- observers ---//
    private fun observeOrdersLiveData() {
        binding.apply {
            viewModel.profileOrdersLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        ordersList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        ordersList.hideShimmer()
                        response.data?.let { data ->
                            if (data.data.isNotEmpty()) {
                                initRecycler(data.data)
                            } else {
                                emptyLay.isVisible = true
                                ordersList.isVisible = false
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        ordersList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- recycler ---//
    private fun initRecycler(data: List<ResponseProfileOrdersList.Data>) {
        binding.apply {
            ordersAdapter.setData(data)
            ordersList.setupRecyclerview(LinearLayoutManager(requireContext()), ordersAdapter)
        }
    }

    //--- lifecycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}