package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchContent(
    @SerialName("podcast_id")
    val podcastId: String,
    val name: String,
    val description: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("episode_count")
    val episodeCount: String,
    val duration: String,
    val language: String,
    val priority: String,
    val popularityScore: String,
    val score: String
)