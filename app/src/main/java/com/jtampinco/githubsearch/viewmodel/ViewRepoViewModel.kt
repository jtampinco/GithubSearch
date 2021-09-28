package com.jtampinco.githubsearch.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jtampinco.githubsearch.data.GithubSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewRepoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GithubSearch,
) : ViewModel() {

    private val arguments = savedStateHandle.getLiveData<String>(REPOSITORY_URL)
    val repositoryUrl = arguments.value ?: ""

    companion object {
        const val REPOSITORY_URL = "REPOSITORY_URL"
    }

}

