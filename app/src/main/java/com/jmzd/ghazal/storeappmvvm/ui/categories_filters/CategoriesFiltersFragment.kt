package com.jmzd.ghazal.storeappmvvm.ui.categories_filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.search_filter.FilterModel
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCategoriesFiltersBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.viewmodel.CategoryProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFiltersFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCategoriesFiltersBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<CategoryProductsViewModel>()

    //variables
    private var minPrice: String? = null
    private var maxPrice: String? = null
    private var sort: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesFiltersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //range slider
        initPriceRange()

        //search filters
        viewModel.getFilters()
        observeSearchFilters()


        //init views
        binding.apply {

            //Rtl scrollview
            lifecycleScope.launch {
                delay(100)
                sortScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }


        }
    }

    //--- observers ---//
    private fun observeSearchFilters() {
        viewModel.filterLiveData.observe(viewLifecycleOwner) {
            setupChip(it)
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


    //--- search filters chip group ---//
    private fun setupChip(list: MutableList<FilterModel>) {
        var tempList = mutableListOf<FilterModel>()
        tempList.clear()
        tempList = list
        tempList.forEach {
            val chip = Chip(requireContext())
            val drawable = ChipDrawable.createFromAttributes(
                requireContext(), null, 0,
                R.style.FilterChipsBackground
            )
            chip.setChipDrawable(drawable)
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            chip.text = it.faName
            chip.id = it.id
            chip.setTextAppearanceResource(R.style.FilterChipsText)

            binding.sortChipGroup.apply {
                addView(chip)
                //Click
                setOnCheckedStateChangeListener { group, _ ->
                    sort = tempList[group.checkedChipId - 1].enName
                }
            }
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