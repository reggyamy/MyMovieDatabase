package com.reggya.mymoviedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.databinding.ActivityMainBinding
import com.reggya.mymoviedatabase.external.GenresSharePreferences
import com.reggya.mymoviedatabase.ui.viewmodel.MoviesViewModel
import com.reggya.mymoviedatabase.ui.adapter.GenreAdapter
import com.reggya.mymoviedatabase.ui.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MoviesViewModel by viewModels()
    private var genreId = 0
    private var genreSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val genreAdapter = GenreAdapter(true)

        binding.rvSearch.visibility = View.GONE

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collect{
                    if (it is Resource.Success) {
                        it.data?.let { it1 -> genreAdapter.setData(it1) }
                        binding.rvGenre.adapter = genreAdapter
                        genreId = it.data?.get(0)?.id ?: 0
                        genreSize = it.data?.size ?: 0
                        getMovies(genreId)
                        GenresSharePreferences(this@MainActivity).setGenres(it.data)
                    }
                }
            }
        }
        genreAdapter.genreId = { id ->
            getMovies(id)
        }


        binding.btSearch.setOnClickListener {
            setSearch()
        }

        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN && keyCode== KeyEvent.KEYCODE_ENTER){
                setSearch()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.btnFav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

    }

    private fun setSearch() {
        val query = binding.etSearch.text.toString().trim()
        viewModel.searchMovies(query)
        val mAdapter = MoviesAdapter()
        binding.rvSearch.visibility = View.VISIBLE
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultSearch?.collect{
                    mAdapter.submitData(it)
                }
            }
        }
        binding.rvSearch.adapter = mAdapter
    }

    private fun getMovies(genreId: Int) {
        viewModel.getMoviesByGenre(genreId)
        val mAdapter = MoviesAdapter()
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.movies.collect{
                    mAdapter.submitData(it)
                }
            }
        }
        binding.rvMovie.adapter = mAdapter
    }
}