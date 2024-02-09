package com.jmzd.ghazal.storeappmvvm.ui.detail_chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailChartBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.changeVisibility
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailChartFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailChartBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailChartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get id
        viewModel.productIdLiveData.observe(viewLifecycleOwner) {
            if (isNetworkAvailable)
                viewModel.getPriceChart(it)
        }

        //observers
        observePriceChartLiveData()
    }

    //--- observers ---//
    private fun observePriceChartLiveData() {
        binding.apply {
            viewModel.priceChartLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        featuresLoading.changeVisibility(true, pricesChart)
                    }

                    is MyResponse.Success -> {
                        featuresLoading.changeVisibility(false, pricesChart)
                        /*response.data?.let { data ->
                            daysList.clear()
                            daysListForTooltip.clear()
                            pricesList.clear()

                            if (data.isNotEmpty()) {
                                for (i in data.indices) {
                                    daysListForTooltip.add(data[i].day!!)
                                    daysList.add(data[i].day!!.drop(5))
                                    if (data[i].price!! > 0)
                                        pricesList.add(Entry(i.toFloat(), data[i].price!!.toFloat()))
                                }
                                //Init chart
                                lifecycleScope.launch {
                                    delay(100)
                                    if (pricesList.isNotEmpty()) {
                                        pricesChart.setupMyChart(
                                            DaysFormatter(daysList), pricesList, daysList.size,
                                            daysListForTooltip
                                        )
                                    }
                                }
                            }
                        }*/
                    }

                    is MyResponse.Error -> {
                        featuresLoading.changeVisibility(false, pricesChart)
                        root.showSnackBar(response.message!!)
                    }
                }
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