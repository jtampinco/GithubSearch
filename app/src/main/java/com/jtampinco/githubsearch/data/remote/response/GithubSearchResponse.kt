package com.jtampinco.githubsearch.data.remote.response

import com.google.gson.annotations.SerializedName
import com.jtampinco.githubsearch.data.remote.model.GithubSearchDto

data class GithubSearchResponse(
    @SerializedName("total_count")
    val count: Int,
    @SerializedName("incomplete_results")
    val isIncomplete: Boolean,
    @SerializedName("items")
    val items: List<GithubSearchDto>,
)
