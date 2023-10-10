package com.reggya.mymoviedatabase.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.reggya.mymoviedatabase.R
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.databinding.GenreItemBinding

class GenreAdapter(private val clickAble : Boolean) : RecyclerView.Adapter<GenreAdapter.ViewHolder>(){

    val data = ArrayList<GenresItem?>()
    var genreId: ((Int) -> Unit)? = null
    var indexSelected = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(genreIds: List<GenresItem?>) {
        if (genreIds.isEmpty()) return
        data.clear()
        data.addAll(genreIds)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = data[position]
        if (genre != null) {
            holder.bind(genre, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(genre: GenresItem, position: Int) {
            binding.genre.text = genre.name
            itemView.setOnClickListener {
                indexSelected = position
                genreId?.invoke(genre.id!!)
                notifyDataSetChanged()
            }
            if (indexSelected == position && clickAble){
                binding.genre.setBackgroundResource(R.drawable.bg_border_yellow)
                binding.genre.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow))
            }else {
                binding.genre.setBackgroundResource(R.drawable.bg_border_cardview)
                binding.genre.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            }
        }
    }
}