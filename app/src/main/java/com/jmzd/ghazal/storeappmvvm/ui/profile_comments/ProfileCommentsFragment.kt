package com.jmzd.ghazal.storeappmvvm.ui.profile_comments

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
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseProfileComments
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileCommentsBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileCommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileCommentsFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileCommentsBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileCommentsViewModel>()

    //adapters
    @Inject
    lateinit var commentsAdapter : CommentsAdapter

    //recycler view state
    private var recyclerviewState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileCommentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call api
        if(isNetworkAvailable){
            viewModel.getProfileComments()
        }
        //init views
        binding.apply {
            //Toolbar
            toolbar.apply {
                toolbarTitleTxt.text = getString(R.string.yourComments)
                toolbarOptionImg.isVisible = false
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
            }
        }

        //observers
        observeProfileCommentsLiveData()
        observeDeleteCommentsLiveData()
    }


    //--- observers ---//
    private fun observeProfileCommentsLiveData() {
        binding.apply {
            viewModel.profileCommentsLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        commentsList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        commentsList.hideShimmer()
                        response.data?.let { data ->
                            if (data.data.isNotEmpty()) {
                                initCommentsRecycler(data.data)
                            } else {
                                emptyLay.isVisible = true
                                commentsList.isVisible = false
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        commentsList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun observeDeleteCommentsLiveData() {
        binding.apply {
            viewModel.deleteCommentLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {}

                    is MyResponse.Success -> {
                        response.data?.let {
                            if (isNetworkAvailable)
                                viewModel.getProfileComments()
                        }
                    }

                    is MyResponse.Error -> {
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- init recycler ---//
    private fun initCommentsRecycler(data: List<ResponseProfileComments.Data>) {
        binding.apply {
            commentsAdapter.setData(data)
            commentsList.setupRecyclerview(LinearLayoutManager(requireContext()), commentsAdapter)
            //Auto scroll
            commentsList.layoutManager?.onRestoreInstanceState(recyclerviewState)
            //Click
            commentsAdapter.setOnItemClickListener {
//                //Save state
                recyclerviewState = commentsList.layoutManager?.onSaveInstanceState()
//                //Call delete api
                if (isNetworkAvailable)
                    viewModel.deleteComment(it)
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