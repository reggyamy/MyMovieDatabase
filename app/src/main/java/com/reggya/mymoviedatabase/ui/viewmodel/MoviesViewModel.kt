package com.reggya.mymoviedatabase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.keyta.moviedatabase.data.model.VideosItem
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.data.remote.response.MoviesItem
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.domain.usecase.MoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor
): ViewModel(){

    // from network
    init {
        getGenres()
        getFavoriteMovie()
    }

    private var _movies= MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    var movies : StateFlow<PagingData<Movie>> = _movies
    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            moviesInteractor.getMoviesByGenre(genreId)
                .collect {
                    _movies.value = it
                }
        }
    }

    private val _genres = MutableStateFlow<Resource<List<GenresItem>>>(Resource.Loading())
    val genres =  _genres
    private fun getGenres() {
        viewModelScope.launch {
            moviesInteractor.getGenresMovie()
                .collect {
                    _genres.value = it
                }
        }
    }

    private val _resultsSearch = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val resultSearch : StateFlow<PagingData<Movie>>? = _movies
    fun searchMovies(query: String) {
        viewModelScope.launch {
            moviesInteractor.searchMovies(query)
                .collect {
                    _resultsSearch.value = it
                }
        }
    }

    private var _video = MutableStateFlow<Resource<List<VideosItem>>>(Resource.Loading())
    var video : StateFlow<Resource<List<VideosItem>>> =  _video
    fun getVideo(id: Int) {
        viewModelScope.launch {
            moviesInteractor.getVideos(id).collect{
                _video.value = it
            }
        }
    }

    private var _reviews = MutableStateFlow<PagingData<ResultsItem>>(PagingData.empty())
    var review : StateFlow<PagingData<ResultsItem>> =  _reviews
    fun getReviews(movieId: Int){
        viewModelScope.launch {
            moviesInteractor.getReviews(movieId).collect{
                _reviews.value = it
            }
        }
    }

    // Local
    fun insertFavoriteMovie(movie: Movie) = viewModelScope.launch {
        moviesInteractor.insertFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: Movie) = viewModelScope.launch {
        moviesInteractor.deleteFavoriteMovie(movie)
    }

    private var _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    var favoriteMovies : StateFlow<List<Movie>> = _favoriteMovies
    private fun getFavoriteMovie() {
        viewModelScope.launch {
            moviesInteractor.getFavoriteMovies().collect {
                _favoriteMovies.value = it
            }
        }
    }
}