package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Content(
    val name: String? = null,
    val description: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    val duration: Int? = null,
    val score: Double? = null,
    @SerialName("episode_count")
    val episodeCount: Int? = null,
    val language: String? = null,
    val priority: Int? = null,
    @SerialName("popularityScore")
    val popularityScore: Int? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
)
