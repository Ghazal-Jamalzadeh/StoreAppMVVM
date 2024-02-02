package com.jmzd.ghazal.storeappmvvm.ui.detail_comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.data.models.comments.ResponseCommentsList
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailCommentsBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.changeVisibility
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCommentsFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailCommentsBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailCommentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get id
        viewModel.productIdLiveData.observe(viewLifecycleOwner) {
            if (isNetworkAvailable)
                viewModel.getComments(it)
        }


        //init views
        binding.apply {

        }

        //observers
        observeCommentsLiveData()
    }


    private fun observeCommentsLiveData() {
        binding.apply {
            viewModel.commentsLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        commentsLoading.changeVisibility(true, commentsList)
                    }

                    is MyResponse.Success -> {
                        commentsLoading.changeVisibility(false, commentsList)
                        response.data?.let { data ->
                            if (data.data.isNotEmpty()) {
                                initRecycler(data.data)
                                emptyLay.isVisible = false
                            } else {
                                emptyLay.isVisible = true
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        commentsLoading.changeVisibility(false, commentsList)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initRecycler(data: List<ResponseCommentsList.Data>) {
//        commentsAdapter.setData(data)
//        binding.commentsList.setupRecyclerview(
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true), commentsAdapter
//        )
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}