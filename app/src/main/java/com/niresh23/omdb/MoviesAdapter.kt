package com.niresh23.omdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niresh23.omdb.data.SearchData
import com.niresh23.omdb.databinding.SearchDataViewHolderBinding

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val searchDataList = arrayListOf<SearchData>()

    inner class ViewHolder(val binding: SearchDataViewHolderBinding): RecyclerView.ViewHolder(binding.root)

    private var actionClickListener: ActionClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SearchDataViewHolderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchData = searchDataList[position]
        val resources = holder.itemView.context.resources
        Glide.with(holder.itemView)
            .load(searchData.poster)
            .into(holder.binding.posterIv)

        holder.binding.likeBtn.setOnClickListener {
            actionClickListener?.onLikeClick(searchData, position)
        }

        holder.binding.commentBtn.setOnClickListener {
            actionClickListener?.onCommentClick(searchData, position)
        }

        holder.binding.root.setOnClickListener {
            actionClickListener?.onItemClick(searchData, position)
        }

        if (searchData.isFavorite) {
            holder.binding.likeBtn.setImageResource(R.drawable.liked)
        } else {
            holder.binding.likeBtn.setImageResource(R.drawable.like)
        }

        if (searchData.commentCount > 0) {
            holder.binding.commentCount.text =
                resources.getString(R.string.comments_count_holder, searchData.commentCount)
        } else {
            holder.binding.commentCount.text = ""
        }

        holder.binding.titleTv.text = searchData.title
        holder.binding.typeTv.text = searchData.type
        holder.binding.yearTv.text = searchData.year
    }

    override fun getItemCount(): Int = searchDataList.size

    fun setSearchDataList(searchDataList: List<SearchData>) {
        val oldListSize = this.searchDataList.size
        val newListSize = searchDataList.size
        this.searchDataList.clear()
        this.searchDataList.addAll(searchDataList)
        if (newListSize >= oldListSize) {
            notifyItemRangeChanged(0, oldListSize)
            notifyItemRangeInserted(oldListSize - 1, newListSize - oldListSize)
        } else {
            notifyItemRangeChanged(0, newListSize)
            notifyItemRangeRemoved(newListSize - 1, oldListSize - newListSize)
        }
    }

    fun setActionCLickListener(listener: ActionClickListener) {
        this.actionClickListener = listener
    }

    fun setChangedItem(searchData: SearchData) {
        val changedItem = searchDataList.find { it.imdbID == searchData.imdbID }
        val index = searchDataList.indexOf(changedItem)

        if (index >= 0) {
            searchDataList[index] = searchData
            notifyItemChanged(index)
        }
    }

    interface ActionClickListener {
        fun onItemClick(item: SearchData, position: Int)
        fun onLikeClick(item: SearchData, position: Int)
        fun onCommentClick(item: SearchData, position: Int)
    }
}