package com.jtampinco.githubsearch.data.remote.api

import com.jtampinco.githubsearch.data.remote.response.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") countPerPage: Int,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    ): GithubSearchResponse
}