package com.m7.channel.ui.model

import com.m7.channel.R
import com.m7.channel.data.model.Content
import com.m7.channel.data.model.SearchContent
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class UIContent(
    val name: String? = null,
    val avatarUrl: String? = null,
    val duration: Int? = null,
    val episodeCount: Int? = null,
    val releaseDate: String? = null,
) {
    fun getFormatedDuration(
        hourSymbol: String,
        minuteSymbol: String
    ): String {
        if (duration == null || duration < 0) {
            return "0$minuteSymbol"
        }
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60

        val str = StringBuilder()
        if (hours > 0) {
            str.append("$hours$hourSymbol")
        }
        if (minutes > 0) {
            str.append("$minutes$minuteSymbol")
        }

        return if (str.isEmpty()) "0$minuteSymbol" else str.toString()
    }

    @OptIn(ExperimentalTime::class)
    fun getFormatedDate(): Pair<Int, Int>? {
        if (releaseDate.isNullOrEmpty()) {
            return null
        }
        try {
            val period = Instant
                .parse(releaseDate)
                .periodUntil(Clock.System.now(), TimeZone.UTC)

            val daysSince = period.days
            return when {
                daysSince == 0 -> R.string.hours_ago to period.hours
                daysSince == 1 -> R.string.yesterday to -1
                daysSince == 2 -> R.string.day_before_yesterday to -1
                daysSince > 10 -> R.string.day_ago to daysSince
                else -> R.string.days_ago to daysSince
            }
        } catch (e: IllegalArgumentException) {
            println("Error parsing date: $e")
            return null
        } catch (e: Exception) {
            println("An unexpected error occurred during date formatting: $e")
            return null
        }
    }
}

fun Content.toUIContent(): UIContent {
    return UIContent(
        name,
        avatarUrl,
        duration,
        episodeCount,
        releaseDate
    )
}

fun SearchContent.toUIContent(): UIContent {
    return UIContent(
        name,
        avatarUrl,
        duration.toIntOrNull(),
        episodeCount.toIntOrNull(),
        null
    )
}
