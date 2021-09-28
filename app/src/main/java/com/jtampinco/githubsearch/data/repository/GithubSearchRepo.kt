package com.jtampinco.githubsearch.data.repository

import com.jtampinco.githubsearch.data.base.BaseRemoteRepo
import com.jtampinco.githubsearch.data.base.RepoResponse
import com.jtampinco.githubsearch.data.domain.model.Repository
import com.jtampinco.githubsearch.data.remote.GithubSearchDtoMapper
import com.jtampinco.githubsearch.data.remote.api.GithubService
import javax.inject.Inject

class GithubSearchRepo @Inject constructor(
    private val service: GithubService,
    private val mapper: GithubSearchDtoMapper,
) : BaseRemoteRepo() {

    suspend fun search(
        query: String,
        page: Int,
        countPerPage: Int,
        sort: String? = null,
        order: String? = null,
    ): RepoResponse<List<Repository>> {
        return callApi {
            val apiResponse = service.search(
                query = query,
                page = page,
                countPerPage = countPerPage,
                sort = sort,
                order = order
            ).items
            apiResponse.map { mapper.mapToDomainModel(it) }
        }
    }
}