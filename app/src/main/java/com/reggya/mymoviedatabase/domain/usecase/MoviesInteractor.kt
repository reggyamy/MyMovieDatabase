package com.reggya.mymoviedatabase.domain.usecase

import androidx.paging.PagingData
import com.keyta.moviedatabase.data.model.VideosItem
import com.reggya.mymoviedatabase.data.MoviesRepositoryImpl
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesInteractor @Inject constructor(private val iMoviesRepository: MoviesRepositoryImpl): MoviesUseCase {
    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return iMoviesRepository.getMoviesByGenre(genreId)
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return iMoviesRepository.searchMovies(query)
    }

    override fun getGenresMovie(): Flow<Resource<List<GenresItem>>> {
        return iMoviesRepository.getGenresMovie()
    }

    override fun getReviews(movieId: Int): Flow<PagingData<ResultsItem>> {
        return iMoviesRepository.getReviews(movieId)
    }

    override fun getVideos(movieId: Int): Flow<Resource<List<VideosItem>>> {
        return iMoviesRepository.getVideos(movieId)
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        iMoviesRepository.insertFavoriteMovie(movie)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return iMoviesRepository.getFavoriteMovies()
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        iMoviesRepository.deleteFavoriteMovie(movie)
    }
}