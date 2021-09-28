package com.jtampinco.githubsearch.data.repository

import com.jtampinco.githubsearch.data.GithubSearch
import com.jtampinco.githubsearch.data.base.RepoResponse
import com.jtampinco.githubsearch.data.domain.model.Repository
import javax.inject.Inject

class GithubSearchImpl @Inject constructor(
    private val githubSearchRepo: GithubSearchRepo,
) : GithubSearch {

    override suspend fun search(
        query: String,
        page: Int,
        countPerPage: Int,
        sort: String?,
        order: String?,
    ): RepoResponse<List<Repository>> =
        githubSearchRepo.search(query, page, countPerPage, sort, order)

}