package com.jmzd.ghazal.storeappmvvm.ui.detail_comments_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentAddCommentBinding
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCommentFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<LoginViewModel>()

    //Theme
    override fun getTheme() = R.style.RemoveDialogBackground

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

        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}