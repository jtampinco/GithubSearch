package com.jtampinco.githubsearch.data

import com.jtampinco.githubsearch.data.base.RepoResponse
import com.jtampinco.githubsearch.data.domain.model.Repository

interface GithubSearch {

    suspend fun search(
        query: String,
        page: Int,
        countPerPage: Int,
        sort: String? = null,
        order: String? = null,
    ): RepoResponse<List<Repository>>
}