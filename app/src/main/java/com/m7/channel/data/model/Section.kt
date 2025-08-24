package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Section(
    val name: String,
    val type: String,
    @SerialName("content_type")
    val contentType: String,
    val order: Int,
    val content: List<Content>
)