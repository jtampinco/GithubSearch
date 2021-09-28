package com.jtampinco.githubsearch.data.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val name: String = "",
    val id: Int = 0,
    val avatar_url: String = "",
) : Parcelable