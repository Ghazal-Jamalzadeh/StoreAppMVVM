package com.jmzd.ghazal.storeappmvvm.ui.search
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.search_filter.FilterModel
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSearchFilterBinding
import com.jmzd.ghazal.storeappmvvm.ui.search.adapters.FilterAdapter
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFilterFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<SearchViewModel>()

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

    //Adapters
    @Inject lateinit var adapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init views
        binding.apply {
            //Close
            closeImg.setOnClickListener { this@SearchFilterFragment.dismiss() }
        }
        //Fill filter data
        viewModel.getFilters()
        //observers
        observeFiltersLiveData()
    }

    private fun observeFiltersLiveData() {
        viewModel.filterLiveData.observe(this) {
            initFilterRecycler(it)
        }
    }

    private fun initFilterRecycler(data: List<FilterModel>) {
        adapter.setData(data)
        binding.filtersList.setupRecyclerview(LinearLayoutManager(requireContext()), adapter)
        //Click
        adapter.setOnItemClickListener {
//            viewModel.sendSelectedFilterItem(it)
            this.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}