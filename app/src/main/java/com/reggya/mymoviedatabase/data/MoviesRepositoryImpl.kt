package com.reggya.mymoviedatabase.data

import androidx.paging.PagingData
import com.keyta.moviedatabase.data.model.VideosItem
import com.reggya.mymoviedatabase.data.local.LocalDataSource
import com.reggya.mymoviedatabase.data.remote.MoviesRemoteDataSource
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import com.reggya.mymoviedatabase.domain.repository.IMoviesRepository
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.external.MoviesDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: LocalDataSource
): IMoviesRepository {
    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return moviesRemoteDataSource.getMoviesByGenre(genreId)
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return moviesRemoteDataSource.getSearchMovies(query)
    }

    override fun getGenresMovie(): Flow<Resource<List<GenresItem>>> {
        return moviesRemoteDataSource.getGenres()
    }

    override fun getReviews(movieId: Int): Flow<PagingData<ResultsItem>> {
        return moviesRemoteDataSource.getReviews(movieId)
    }

    override fun getVideos(movieId: Int): Flow<Resource<List<VideosItem>>> {
        return moviesRemoteDataSource.getVideos(movieId)
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        localDataSource.insertMovies(MoviesDataMapper.domainToEntity(movie))
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return flow {
            localDataSource.getMovies().collect{entities ->
                emit(MoviesDataMapper.entityToDomain(entities))
            }
        }
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        localDataSource.deleteMovie(MoviesDataMapper.domainToEntity(movie))
    }
}