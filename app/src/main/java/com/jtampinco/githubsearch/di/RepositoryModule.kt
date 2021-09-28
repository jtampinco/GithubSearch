package com.jtampinco.githubsearch.di

import com.jtampinco.githubsearch.data.GithubSearch
import com.jtampinco.githubsearch.data.SearchSettings
import com.jtampinco.githubsearch.data.repository.GithubSearchImpl
import com.jtampinco.githubsearch.data.repository.SearchSettingsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GithubSearchClass() {

    @Binds
    abstract fun bindGithubSearchRepository(
        githubSearchImpl: GithubSearchImpl,
    ): GithubSearch

    @Binds
    abstract fun bindSearchSettings(
        searchSettingsImpl: SearchSettingsImpl,
    ): SearchSettings
}