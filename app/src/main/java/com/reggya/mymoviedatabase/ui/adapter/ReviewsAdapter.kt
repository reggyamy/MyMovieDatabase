package com.reggya.mymoviedatabase.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import com.reggya.mymoviedatabase.databinding.ReviewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ReviewsAdapter: PagingDataAdapter<ResultsItem, ReviewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: ReviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data: ResultsItem) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = data.createdAt?.let { formatter.parse(it) }
            val desiredFormat = SimpleDateFormat("dd, MMM yyyy").format(date)
            binding.username.text = data.authorDetails?.username ?: "Anonim"
            binding.date.text = desiredFormat
            binding.content.text = data.content
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>(){
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
