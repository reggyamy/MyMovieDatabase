package com.reggya.mymoviedatabase

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.databinding.ActivityDetailBinding
import com.reggya.mymoviedatabase.databinding.ContentDetailBinding
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.external.GenresSharePreferences
import com.reggya.mymoviedatabase.ui.viewmodel.MoviesViewModel
import com.reggya.mymoviedatabase.ui.adapter.GenreAdapter
import com.reggya.mymoviedatabase.ui.adapter.ReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE = "movie"
    }
    private lateinit var mBinding : ActivityDetailBinding
    private lateinit var binding: ContentDetailBinding
    private val viewModel : MoviesViewModel by viewModels()
    private var state = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailBinding.inflate(layoutInflater)
        binding = mBinding.content
        setContentView(mBinding.root)

        supportActionBar?.title = "Detail Movie"

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_MOVIE)
        }

        if (movie != null) {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = movie.releaseDate?.let { formatter.parse(it) }
            val desiredFormat = SimpleDateFormat("dd, MMM yyyy").format(date)
            binding.title.text = movie.title
            binding.releaseDate.text = "Release :$desiredFormat"
            binding.count.text = "Votes Count : ${movie.voteCount}"
            binding.voteAverage.text = "Vote average : ${movie.voteAverage}"
            binding.popularity.text = "Popularity : ${movie.popularity}"
            binding.overview.text = movie.overview
            Glide.with(this).load(BuildConfig.IMAGE_URL +movie.posterPath).into(binding.poster)
        }

        val reviewAdapter = ReviewsAdapter()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.video?.collect{
                    if (it is Resource.Success){
                        val trailer = it.data?.find { videosItem ->
                            videosItem.type == "Trailer"
                        }
                        lifecycle.addObserver(binding.video)
                        binding.video.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = trailer?.key
                                if (videoId != null){
                                    youTubePlayer.loadVideo(videoId, 0f)
                                }
                            }
                        })
                    }
                }

                viewModel.favoriteMovies?.collect {entities ->
                    if (entities.any { it.id == movie?.id }){
                        setUpState(true)
                    } else {
                        setUpState(false)
                    }
                }
            }
        }

        val genreAdapter = GenreAdapter(false)
        val genres = GenresSharePreferences(this).getGenres()
        if (genres != null) {
            genreAdapter.setData(genres)
        }

        binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenre.adapter = genreAdapter

        binding.save.setOnClickListener {
            if (state){
                setUpState(state)
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        if (movie != null) {
                            viewModel.insertFavoriteMovie(movie)
                        }
                    }
                }
            }else {
                setUpState(state)
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        if (movie != null) {
                            viewModel.deleteFavoriteMovie(movie)
                        }
                    }
                }

            }
        }
    }

    private fun setUpState(mState: Boolean){
        state = if (mState) {
            binding.save.setImageResource(R.drawable.round_bookmark_24)
            !state
        } else {
            binding.save.setImageResource(R.drawable.round_bookmark_border_24)
            !state
        }
    }
}