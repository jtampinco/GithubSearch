package com.jtampinco.githubsearch.data.remote.model

import com.google.gson.annotations.SerializedName

data class LicenseDto(

    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("spdx_id") val spdx_id: String,
    @SerializedName("url") val url: String,
    @SerializedName("node_id") val node_id: String,
)