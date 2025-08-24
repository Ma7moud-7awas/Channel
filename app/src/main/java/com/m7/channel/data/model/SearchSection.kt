package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchSection(
    val name: String,
    val type: String,
    @SerialName("content_type")
    val contentType: String,
    val order: String,
    val content: List<SearchContent>
)