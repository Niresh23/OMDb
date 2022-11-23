package com.niresh23.omdb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.niresh23.omdb.data.SearchData
import com.niresh23.omdb.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val databaseViewModel: DatabaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        databaseViewModel.addedInFavoriteLiveData.observe(viewLifecycleOwner) { searchData ->
            setFavoriteBtn(searchData)
        }

        databaseViewModel.commentCountLiveData.observe(viewLifecycleOwner) {
            binding.detailCard.imdbBtn.text = it.second.toString()
        }

        mainViewModel.showDetailsLiveData.observe(viewLifecycleOwner) { searchData ->
            Glide.with(this).load(searchData.poster).into(binding.detailCard.posterIv)
            binding.detailCard.titleTv.text = searchData.title
            binding.detailCard.typeTv.text = searchData.type
            binding.detailCard.yearTv.text = searchData.year
            binding.detailCard.imdbBtn.setOnClickListener {
                mainViewModel.openOnImdbWebSite(searchData.imdbID)
            }

            setFavoriteBtn(searchData)

            binding.detailCard.likeBtn.setOnClickListener {
                databaseViewModel.onLikeClicked(searchData)
            }

            binding.commentCard.commentBtn.setOnClickListener {
                val comment = binding.commentCard.pageEt.text.toString()
                if (comment.isNotEmpty())
                    databaseViewModel.saveComment(searchData.imdbID, comment)
            }

            databaseViewModel.getCommentCount(searchData.imdbID)
        }
    }

    private fun setFavoriteBtn(searchData: SearchData) {
        if (searchData.isFavorite) {
            binding.detailCard.likeBtn.setImageResource(R.drawable.liked)
        } else {
            binding.detailCard.likeBtn.setImageResource(R.drawable.like)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}