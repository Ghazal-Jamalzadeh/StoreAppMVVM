package com.jmzd.ghazal.storeappmvvm.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseCartList
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCartBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.constants.DECREMENT
import com.jmzd.ghazal.storeappmvvm.utils.constants.DELETE
import com.jmzd.ghazal.storeappmvvm.utils.constants.INCREMENT
import com.jmzd.ghazal.storeappmvvm.utils.events.EventBus
import com.jmzd.ghazal.storeappmvvm.utils.events.Events
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<CartViewModel>()

    //adapter
    @Inject
    lateinit var cartAdapter : CartAdapter

    //save recycler view state
    private var recyclerViewState: Parcelable? = null

    //flags
    //single observe ham mishod estefade kard ke too project haye ghabl zadim
    //vali in ravesh flag gozari ham ok e kamelan kar mikone
    private var isNavigateToShipping : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call api
        if(isNetworkAvailable){
            viewModel.getCartList()
        }

        //init views
        binding.apply {
            //Fab animation
            cartsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && continueFABtn.isExtended)
                        continueFABtn.shrink()
                    else if (dy < 0 && !continueFABtn.isExtended)
                        continueFABtn.extend()
                }
            })
        }

        //observers
        observeCartListLiveData()
        observeUpdateCartLiveData()
        observeContinueLiveData()
    }

    //--- observers ---//

    @SuppressLint("SetTextI18n")
    private fun observeCartListLiveData() {
        binding.apply {
            viewModel.cartListLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loading.isVisible = true
                    }

                    is NetworkRequest.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            if (data.orderItems != null) {
                                if (data.orderItems.isNotEmpty()) {
                                    emptyLay.isVisible = false
                                    cartsList.isVisible = true
                                    //show fab (continue)
                                    continueFABtn.isVisible = true
                                    continueFABtn.setOnClickListener {
                                        isNavigateToShipping = true
                                        if (isNetworkAvailable)
                                            viewModel.cartContinue()
                                    }
                                    //Toolbar txt
                                    toolbarPriceTxt.text = data.itemsPrice.toString().toInt().moneySeparating()
                                    initRecyclerView(data.orderItems)
                                } else {
                                    emptyLay.isVisible = true
                                    cartsList.isVisible = false
                                    continueFABtn.isVisible = false
                                    toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"
                                }
                            } else {
                                emptyLay.isVisible = true
                                cartsList.isVisible = false
                                continueFABtn.isVisible = false
                                toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun observeUpdateCartLiveData() {
        binding.apply {
            viewModel.updateCartLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {}

                    is NetworkRequest.Success -> {
                        response.data?.let {
                            //update result
                            if (isNetworkAvailable)
                                viewModel.getCartList()
                            //Update badge
                            lifecycleScope.launch {
                                EventBus.publish(Events.IsUpdateCart)
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- recycler ---//
    private fun initRecyclerView(list: List<ResponseCartList.OrderItem>) {
        cartAdapter.setData(list)
        val linearlayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            cartsList.setupRecyclerview(linearlayoutManager, cartAdapter)
            //Auto scroll
            cartsList.layoutManager?.onRestoreInstanceState(recyclerViewState)
            //Click
            cartAdapter.setOnItemClickListener { id, type ->
                //Save state
                recyclerViewState = cartsList.layoutManager?.onSaveInstanceState()
                //handle clicks
                when (type) {
                    INCREMENT -> {
                        if (isNetworkAvailable)
                            viewModel.increment(id)
                    }

                    DECREMENT -> {
                        if (isNetworkAvailable)
                            viewModel.decrement(id)
                    }

                    DELETE -> {
                        if (isNetworkAvailable)
                            viewModel.deleteProduct(id)
                    }
                }
            }
        }
    }

    private fun observeContinueLiveData() {
        binding.apply {
            viewModel.continueLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {}

                    is NetworkRequest.Success -> {
                        response.data?.let {
                            //Navigate
                            if (isNavigateToShipping)
                                findNavController().navigate(R.id.action_to_shipping_fragment)
                            isNavigateToShipping = false
                        }
                    }

                    is NetworkRequest.Error -> {
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        //we don't need this piece of code in current scenario
        //در صورتی که موقع برگشت از صفحه شیپمنت نیاز به آپدیت بج یا لیست داشتید اینجا صدا بزنید

        //Update badge
        lifecycleScope.launch {
            EventBus.publish(Events.IsUpdateCart)
        }
    }


    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}