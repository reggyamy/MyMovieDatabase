package com.reggya.mymoviedatabase.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reggya.mymoviedatabase.BuildConfig
import com.reggya.mymoviedatabase.data.remote.network.ApiService
import com.reggya.mymoviedatabase.data.remote.response.MoviesItem
import com.reggya.mymoviedatabase.domain.model.Movie
import com.reggya.mymoviedatabase.external.MoviesDataMapper
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource (
    private val apiService: ApiService,
    private val query: String
): PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPage = state.anchorPosition?.let {
            state.closestPageToPosition(it)
        }
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: 1
        return try {
            val client = apiService.searchMovie(BuildConfig.API_KEY, query, pageIndex)
            val response = MoviesDataMapper.responseToDomain(client.results)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}