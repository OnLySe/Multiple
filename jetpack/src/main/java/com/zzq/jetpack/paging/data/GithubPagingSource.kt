package com.zzq.jetpack.paging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.codelabs.paging.model.Repo
import com.zzq.jetpack.paging.api.GithubService
import com.zzq.jetpack.paging.data.GithubRepository.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

const val IN_QUALIFIER = "in:name,description"

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, Repo>() {

    /**
     * 以异步方式提取更多数据，用于在用户滚动过程中显示
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = service.searchRepos(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    /**
     * 刷新键用于对 PagingSource.load() 的后续刷新调用（首次调用为初始加载，使用的是 Pager 提供的 initialKey）。
     * 每次 Paging 库要加载新数据来替代当前列表时（例如：滑动刷新时，或因数据库更新、配置更改、进程终止等原因而出现无效
     * 现象时，等等），都会发生刷新。通常，后续刷新调用将需要重新开始加载以 PagingState.anchorPosition（表示最近一
     * 次访问过的索引）为中心的数据
     */
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}