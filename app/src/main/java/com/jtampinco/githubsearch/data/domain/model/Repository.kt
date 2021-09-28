package com.jtampinco.githubsearch.data.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val url: String,
    val description: String? = "",
    val lastUpdated: String? = "",
    val forksCount: Int = 0,
    val issuesCount: Int = 0,
    val watchersCount: Int = 0,
    val score: Float = 0.0f,
    val owner: Owner,
) : Parcelable



