package com.jmzd.ghazal.storeappmvvm.ui.detail_comments_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.comments.BodySendComment
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentAddCommentBinding
import com.jmzd.ghazal.storeappmvvm.utils.constants.PRODUCT_ID
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddCommentFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<DetailViewModel>()

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

    //body
    @Inject
    lateinit var body : BodySendComment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {

            //Close
            closeImg.setOnClickListener { this@AddCommentFragment.dismiss() }


            //Submit
            submitBtn.setOnClickListener {
                val rate = rateSlider.value.toInt().toString()
                val comment = commentEdt.text.toString()
                if (comment.isEmpty())
                    root.showSnackBar(getString(R.string.notEmptyComment))
                else {
                    body.rate = rate
                    body.comment = comment
                    viewModel.sendComment(PRODUCT_ID, body)
                }
            }
        }

        //observers
        observeSendCommentLiveData()
    }

    //--- observers ---//
    private fun observeSendCommentLiveData() {
        binding.apply {
            viewModel.sendCommentLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        submitBtn.enableLoading(true)
                    }

                    is MyResponse.Success -> {
                        submitBtn.enableLoading(false)
                        response.data?.let { data ->
                            Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
                            this@AddCommentFragment.dismiss()
                        }
                    }

                    is MyResponse.Error -> {
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