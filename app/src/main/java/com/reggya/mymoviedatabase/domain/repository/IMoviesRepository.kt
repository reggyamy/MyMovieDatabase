package com.reggya.mymoviedatabase.domain.repository

import androidx.paging.PagingData
import com.keyta.moviedatabase.data.model.VideosItem
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    fun getGenresMovie(): Flow<Resource<List<GenresItem>>>
    fun getReviews(movieId: Int): Flow<PagingData<ResultsItem>>
    fun getVideos(movieId: Int): Flow<Resource<List<VideosItem>>>
    suspend fun insertFavoriteMovie(movie: Movie)
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun deleteFavoriteMovie(movie: Movie)
}
