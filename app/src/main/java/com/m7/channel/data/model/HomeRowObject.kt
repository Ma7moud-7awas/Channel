package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SectionsRowObject(
    val sections: List<Section>,
    val pagination: Pagination? = null
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Pagination(
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("total_pages")
    val totalPages: Int
)
