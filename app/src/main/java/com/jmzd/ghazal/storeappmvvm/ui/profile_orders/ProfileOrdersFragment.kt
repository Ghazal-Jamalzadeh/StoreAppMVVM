package com.jmzd.ghazal.storeappmvvm.ui.profile_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileOrdersBinding
import com.jmzd.ghazal.storeappmvvm.utils.CANCELED
import com.jmzd.ghazal.storeappmvvm.utils.DELIVERED
import com.jmzd.ghazal.storeappmvvm.utils.PENDING
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileOrdersFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileOrdersBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileOrdersViewModel>()

    //args
    private val args by navArgs<ProfileOrdersFragmentArgs>()

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
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}