package com.example.bboxnewstask.utils

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object ExtensionFunctions {

    fun String.toTimeAgo(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(this)
        val now = Date()

        val diff = now.time - date.time
        val seconds = abs(diff / 1000)
        val minutes = abs(diff / (60 * 1000))
        val hours = abs(diff / (60 * 60 * 1000))
        val days = abs(diff / (24 * 60 * 60 * 1000))
        val weeks = abs(diff / (7 * 24 * 60 * 60 * 1000))
        val months = abs(diff / (30 * 24 * 60 * 60 * 1000))
        val years = abs(diff / (365 * 24 * 60 * 60 * 1000))

        return when {
            seconds < 60 -> "$seconds seconds ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            days < 7 -> "$days days ago"
            weeks < 4 -> "$weeks weeks ago"
            months < 12 -> "$months months ago"
            else -> "$years years ago"
        }
    }

}

