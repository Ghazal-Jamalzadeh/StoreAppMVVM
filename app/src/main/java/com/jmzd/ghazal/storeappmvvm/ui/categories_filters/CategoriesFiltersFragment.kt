package com.jmzd.ghazal.storeappmvvm.ui.categories_filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesFiltersBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFiltersFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesFiltersBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //variables
    private var minPrice: String? = null
    private var maxPrice: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesFiltersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {

            //range slider
            initPriceRange()


        }
    }

    //--- range slider ---//
    private fun initPriceRange() {
        //Label format
        val formatter = LabelFormatter { value ->
            value.toInt().moneySeparating()
        }
        //Customize
        binding.priceRange.apply {
            setValues(7000000f, 21000000f)
            setLabelFormatter(formatter)
            //Listener
            addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) {

                }

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    val values = slider.values
                    minPrice = values[0].toInt().toString()
                    maxPrice = values[1].toInt().toString()
                }
            })
        }
    }

    //--- life cycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}