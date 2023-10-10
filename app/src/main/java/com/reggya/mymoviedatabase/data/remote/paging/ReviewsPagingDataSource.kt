package com.reggya.mymoviedatabase.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reggya.mymoviedatabase.data.remote.network.ApiService
import com.reggya.mymoviedatabase.data.remote.response.ResultsItem
import retrofit2.HttpException
import java.io.IOException

class ReviewsPagingDataSource (private val apiService: ApiService, private val movieId: Int): PagingSource<Int, ResultsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        val anchorPage = state.anchorPosition?.let {
            state.closestPageToPosition(it)
        }
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val pageIndex = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val client = apiService.getUsersReview(movieId, pageIndex)
            val response = client.results
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNullOrEmpty()) null else pageIndex + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}