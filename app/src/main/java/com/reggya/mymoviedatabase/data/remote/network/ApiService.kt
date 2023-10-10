package com.reggya.mymoviedatabase.data.remote.network

import com.keyta.moviedatabase.data.model.VideosResponse
import com.reggya.mymoviedatabase.BuildConfig
import com.reggya.mymoviedatabase.data.remote.response.GenresResponse
import com.reggya.mymoviedatabase.data.remote.response.MoviesResponse
import com.reggya.mymoviedatabase.data.remote.response.UsersReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genre: Int,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenresMovie(
        @Query("api_key") apiKey: String
    ): GenresResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}/reviews?api_key=${BuildConfig.API_KEY}")
    suspend fun getUsersReview(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): UsersReviewResponse

    @GET("movie/{movie_id}/videos?api_key=${BuildConfig.API_KEY}")
    suspend fun getVideo(
        @Path("movie_id") movieId: Int
    ): VideosResponse

}