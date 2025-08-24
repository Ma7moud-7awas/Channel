package com.m7.channel.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchRowObject(
    val sections: List<SearchSection>
)