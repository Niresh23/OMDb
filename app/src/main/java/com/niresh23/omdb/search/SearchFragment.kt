package com.niresh23.omdb.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.niresh23.omdb.DatabaseViewModel
import com.niresh23.omdb.MainViewModel
import com.niresh23.omdb.MoviesAdapter
import com.niresh23.omdb.data.SearchData
import com.niresh23.omdb.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    private val adapter = MoviesAdapter()
    private val viewModel: SearchViewModel by activityViewModels()
    private val databaseViewModel: DatabaseViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.moviesRv?.layoutManager = layoutManager
        binding?.moviesRv?.adapter = adapter

        adapter.setActionCLickListener(object : MoviesAdapter.ActionClickListener {
            override fun onItemClick(item: SearchData, position: Int) {
                mainViewModel.showDetails(item)
            }

            override fun onLikeClick(item: SearchData, position: Int) {
                databaseViewModel.onLikeClicked(item)
            }

            override fun onCommentClick(item: SearchData, position: Int) {

            }
        })

        observe()
    }

    private fun observe() {
        viewModel.searchDataLiveData.observe(viewLifecycleOwner) { searchDataList ->
            adapter.setSearchDataList(searchDataList)
        }

        databaseViewModel.addedInFavoriteLiveData.observe(viewLifecycleOwner) {
            adapter.setChangedItem(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}