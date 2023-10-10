package com.reggya.mymoviedatabase.external

import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.data.local.entity.MovieEntity
import com.reggya.mymoviedatabase.data.remote.response.MoviesItem


object MoviesDataMapper {
    fun responsesToEntities(responses: List<MoviesItem>): List<MovieEntity> {
        val entities = ArrayList<MovieEntity>()
        for (response in responses){
            val listIds = response.genreIds.map { it.toString() }
            val stringIds = listIds.joinToString(",")
            val entity = MovieEntity(
                id = response.id,
                overview = response.overview,
                originalLanguage = response.originalLanguage,
                originalTitle = response.originalTitle,
                video = response.video,
                title = response.title,
                posterPath = response.posterPath,
                backdropPath = response.backdropPath,
                releaseDate = response.releaseDate,
                popularity = response.popularity,
                voteAverage = response.voteAverage,
                adult = response.adult,
                voteCount = response.voteCount,
                genreIds = stringIds
                )
            entities.add(entity)
        }
        return  entities
    }

    fun entityToDomain(entities: List<MovieEntity>): List<Movie> {
        val movies = ArrayList<Movie>()
        for (response in entities){
            val movie = Movie(
                id = response.id,
                overview = response.overview,
                originalLanguage = response.originalLanguage,
                originalTitle = response.originalTitle,
                video = response.video,
                title = response.title,
                posterPath = response.posterPath,
                backdropPath = response.backdropPath,
                releaseDate = response.releaseDate,
                popularity = response.popularity,
                voteAverage = response.voteAverage,
                adult = response.adult,
                voteCount = response.voteCount,
                genreIds = response.genreIds
            )
            movies.add(movie)
        }
        return  movies
    }

    fun domainToEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            overview = movie.overview,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            video = movie.video,
            title = movie.title,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            releaseDate = movie.releaseDate,
            popularity = movie.popularity,
            voteAverage = movie.voteAverage,
            adult = movie.adult,
            voteCount = movie.voteCount,
            genreIds = movie.genreIds
        )
    }

    fun responseToDomain(responses: List<MoviesItem>): List<Movie> {
        val movies = ArrayList<Movie>()
        for (response in responses){
            val listIds = response.genreIds.map { it.toString() }
            val stringIds = listIds.joinToString(",")
            val movie = Movie(
                id = response.id,
                overview = response.overview,
                originalLanguage = response.originalLanguage,
                originalTitle = response.originalTitle,
                video = response.video,
                title = response.title,
                posterPath = response.posterPath,
                backdropPath = response.backdropPath,
                releaseDate = response.releaseDate,
                popularity = response.popularity,
                voteAverage = response.voteAverage,
                adult = response.adult,
                voteCount = response.voteCount,
                genreIds = stringIds
            )
            movies.add(movie)
        }
        return  movies
    }

}
