package com.reggya.mymoviedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.reggya.mymoviedatabase.databinding.ActivityFavoriteBinding
import com.reggya.mymoviedatabase.ui.adapter.FavoriteMoviesAdapter
import com.reggya.mymoviedatabase.ui.adapter.MoviesAdapter
import com.reggya.mymoviedatabase.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val viewModel : MoviesViewModel by viewModels()
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favoriteMovies.collect(){
                    val mAdapter = FavoriteMoviesAdapter()
                    mAdapter.setData(it)
                    binding.rvMovie.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    binding.rvMovie.adapter = mAdapter
                }
            }
        }
    }
}