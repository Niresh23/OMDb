package com.niresh23.omdb.search

import android.app.Dialog
import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.niresh23.omdb.databinding.SerachParamRequestFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchParamBottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var binding: SerachParamRequestFragmentBinding
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SerachParamRequestFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.applyBtn.setOnClickListener {
            viewModel.searchMovie(binding.titleEt.text.toString(), binding.pageEt.text.toString())
            dismiss()
        }
    }
}