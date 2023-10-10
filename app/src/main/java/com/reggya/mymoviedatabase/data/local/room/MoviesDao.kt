package com.reggya.mymoviedatabase.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reggya.mymoviedatabase.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface MoviesDao {

    @Insert
    suspend fun insertMovie(movies: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

}