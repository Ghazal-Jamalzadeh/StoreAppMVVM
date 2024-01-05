package com.jmzd.ghazal.storeappmvvm.ui.profile_favorite
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProfileFavorites
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileFavoriteBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFavoriteFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileFavoriteBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileFavoritesViewModel>()

    //adapters
    @Inject
    lateinit var favoritesAdapter: FavoritesAdapter

    //recycler view state
    private var recyclerviewState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //Call api
            if (isNetworkAvailable)
                viewModel.getFavorites()
            //InitViews
            binding.apply {
                //Toolbar
                toolbar.apply {
                    toolbarTitleTxt.text = getString(R.string.yourFavorites)
                    toolbarOptionImg.isVisible = false
                    toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
                }
            }
            //Load data
            observeFavoritesLiveData()
            observeDeleteFavoriteLiveData()
        }
    }


    private fun observeFavoritesLiveData() {
        binding.apply {
            viewModel.profileFavoritesLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        favoritesList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        favoritesList.hideShimmer()
                        response.data?.let { data ->
                            if (data.data.isNotEmpty()) {
                                initRecycler(data.data)
                            } else {
                                emptyLay.isVisible = true
                                favoritesList.isVisible = false
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        favoritesList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun observeDeleteFavoriteLiveData() {
        binding.apply {
            viewModel.deleteFavoriteLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {}

                    is MyResponse.Success -> {
                        response.data?.let {
                            if (isNetworkAvailable)
                                viewModel.getFavorites()
                        }
                    }

                    is MyResponse.Error -> {
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initRecycler(data: List<ResponseProfileFavorites.Data>) {
        binding.apply {
            favoritesAdapter.setData(data)
            favoritesList.setupRecyclerview(LinearLayoutManager(requireContext()), favoritesAdapter)
            //Auto scroll
            favoritesList.layoutManager?.onRestoreInstanceState(recyclerviewState)
            //Click
            favoritesAdapter.setOnItemClickListener {
                //Save state
                recyclerviewState = favoritesList.layoutManager?.onSaveInstanceState()
                //Call delete api
                if (isNetworkAvailable)
                    viewModel.deleteFavorite(it)
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