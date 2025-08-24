package com.m7.channel.ui.model

import androidx.annotation.StringRes
import com.m7.channel.R
import com.m7.channel.data.model.SearchSection
import com.m7.channel.data.model.Section

data class UISection(
    val name: String,
    val type: String,
    val contentType: String,
    val order: Int? = null,
    val content: List<UIContent>
)

enum class ContentType(val value: String, @StringRes val resId: Int) {
    EPISODE("episode", R.string.episode),
    PODCAST("podcast", R.string.podcast),
    AUDIOBOOK("audio_book", R.string.audiobook),
    AUDIO_ARTICLE("audio_article", R.string.audio_article);

    companion object {
        @StringRes
        fun resFromValue(value: String): Int? {
            return entries.firstOrNull { it.value == value }?.resId
        }
    }
}

fun Section.toUISection(): UISection {
   return UISection(
        name,
        type,
        contentType,
        order,
        content.map { it.toUIContent() }
    )
}

fun SearchSection.toUISection(): UISection {
    return UISection(
        name,
        type,
        contentType,
        order.toIntOrNull(),
        content.map { it.toUIContent() }
    )
}