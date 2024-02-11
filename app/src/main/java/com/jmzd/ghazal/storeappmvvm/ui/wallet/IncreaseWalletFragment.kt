package com.jmzd.ghazal.storeappmvvm.ui.wallet
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentIncreaseWalletBinding
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.utils.extensions.openBrowser
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncreaseWalletFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentIncreaseWalletBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<WalletViewModel>()

    @Inject
    lateinit var body : BodyIncreaseWallet

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentIncreaseWalletBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Close
            closeImg.setOnClickListener { this@IncreaseWalletFragment.dismiss() }

            //Money separating
            amountEdt.addTextChangedListener {
                if (it.toString().isNotEmpty()) {
                    amountTxt.text = it.toString().trim().toInt().moneySeparating()
                } else {
                    amountTxt.text = ""
                }
            }

            //Click
            submitBtn.setOnClickListener {
                val amount = amountEdt.text.toString()
                if (amount.isNotEmpty()) {
                    body.amount = amount
                    viewModel.increaseWallet(body)
                }
            }

            //observers
            observeIncreaseWalletLiveData()
        }
    }

    //--- observers ---//
    private fun observeIncreaseWalletLiveData() {
        binding.apply {
            viewModel.increaseWalletLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        submitBtn.enableLoading(true)
                    }

                    is NetworkRequest.Success -> {
                        submitBtn.enableLoading(false)
                        response.data?.let { data ->
                            Uri.parse(data.startPay).openBrowser(requireContext())
                            this@IncreaseWalletFragment.dismiss()
                        }
                    }

                    is NetworkRequest.Error -> {
                        submitBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}