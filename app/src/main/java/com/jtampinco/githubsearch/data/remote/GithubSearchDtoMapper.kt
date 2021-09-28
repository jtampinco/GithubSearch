package com.jtampinco.githubsearch.data.remote

import com.jtampinco.githubsearch.data.domain.model.Owner
import com.jtampinco.githubsearch.data.domain.model.Repository
import com.jtampinco.githubsearch.data.domain.util.DomainMapper
import com.jtampinco.githubsearch.data.remote.model.GithubSearchDto
import javax.inject.Inject

class GithubSearchDtoMapper @Inject constructor() : DomainMapper<GithubSearchDto, Repository> {
    override fun mapToDomainModel(model: GithubSearchDto): Repository {
        return Repository(
            id = model.id,
            name = model.name,
            fullName = model.full_name,
            url = model.html_url,
            description = model.description,
            lastUpdated = model.updated_at,
            forksCount = model.forks_count,
            issuesCount = model.open_issues_count,
            watchersCount = model.watchers_count,
            score = model.score,
            owner = Owner(
                name = model.owner.login,
                id = model.owner.id,
                avatar_url = model.owner.avatar_url
            )
        )
    }
}