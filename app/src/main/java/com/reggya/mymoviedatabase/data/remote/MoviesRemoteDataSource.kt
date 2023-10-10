package com.reggya.mymoviedatabase.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keyta.moviedatabase.data.model.VideosItem
import com.reggya.mymoviedatabase.BuildConfig
import com.reggya.mymoviedatabase.data.remote.network.ApiService
import com.reggya.mymoviedatabase.data.remote.paging.MoviesPagingSource
import com.reggya.mymoviedatabase.data.remote.response.Resource
import com.reggya.mymoviedatabase.data.remote.paging.ReviewsPagingDataSource
import com.reggya.mymoviedatabase.data.remote.paging.SearchPagingSource
import com.reggya.mymoviedatabase.data.remote.response.GenresItem
import com.reggya.mymoviedatabase.data.remote.response.MoviesItem
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import com.reggya.mymoviedatabase.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSource @Inject constructor(
    private val apiService: ApiService
){

    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiService, genreId)
            }
        ).flow
    }

    fun getSearchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(apiService, query)
            }
        ).flow
    }

    fun getReviews(movieId: Int): Flow<PagingData<ResultsItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewsPagingDataSource(apiService, movieId)
            }
        ).flow
    }

    fun getVideos(movieId: Int): Flow<Resource<List<VideosItem>>>{
        return flow {
            try {
                val response = apiService.getVideo(movieId).results
                if (response.isNotEmpty()){
                    emit(Resource.Success(response))
                }else emit(Resource.Loading())
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGenres(): Flow<Resource<List<GenresItem>>>{
        return flow {
            try {
                val response = apiService.getGenresMovie(BuildConfig.API_KEY).genres
                if (response.isNotEmpty()){
                    emit(Resource.Success(response))
                }else emit(Resource.Loading())
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}