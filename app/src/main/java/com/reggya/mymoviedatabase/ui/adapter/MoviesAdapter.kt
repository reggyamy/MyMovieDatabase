package com.reggya.mymoviedatabase.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reggya.mymoviedatabase.BuildConfig.IMAGE_URL
import com.reggya.mymoviedatabase.DetailActivity
import com.reggya.mymoviedatabase.DetailActivity.Companion.EXTRA_MOVIE
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.databinding.MovieItemBinding
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.external.GenresSharePreferences
import java.text.SimpleDateFormat
import java.util.Locale

class MoviesAdapter: PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data: Movie) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = data.releaseDate?.let { formatter.parse(it) }
            val desiredFormat = SimpleDateFormat("dd, MMM yyyy").format(date)
            Glide.with(itemView.context).load(IMAGE_URL+data.posterPath).into(binding.image)
            binding.title.text = data.title
            binding.date.text = desiredFormat

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, data)
                itemView.context.startActivity(intent)
            }

            if (data.genreIds != null){
                var genreIds = data.genreIds.split(",").map { it.toInt() }
                if (genreIds.size >= 2) genreIds = genreIds.slice(0..1)
                Log.e("124", genreIds.toString())
                val genreAdapter = GenreAdapter(false)
                binding.rvGenre.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                val genres = ArrayList<GenresItem?>()
                val data_ = GenresSharePreferences(itemView.context).getGenres()
                if (data_?.isNotEmpty() == true) genres.addAll(data_)
                val newData = genres.filter { genreItem ->
                    genreItem?.id in genreIds
                }
                genreAdapter.setData(newData)
                binding.rvGenre.adapter = genreAdapter
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, data)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
