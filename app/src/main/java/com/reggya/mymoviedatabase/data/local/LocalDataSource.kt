package com.reggya.mymoviedatabase.data.local

import com.reggya.mymoviedatabase.data.local.entity.MovieEntity
import com.reggya.mymoviedatabase.data.local.room.MovieDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDatabase: MovieDatabase) {
    private val moviesDao = movieDatabase.movieDao()
    fun getMovies(): Flow<List<MovieEntity>> = moviesDao.getAllMovie()
    suspend fun insertMovies(movie: MovieEntity) = moviesDao.insertMovie(movie)
    suspend fun deleteMovie(movie: MovieEntity) = moviesDao.deleteMovie(movie)
}