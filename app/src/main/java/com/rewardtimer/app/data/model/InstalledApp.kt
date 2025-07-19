package com.rewardtimer.app.data.model

import android.graphics.drawable.Drawable

data class InstalledApp(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val isSystemApp: Boolean = false,
    val isSelected: Boolean = false,
    val timerDurationMinutes: Int = 5, // Default to 5 minutes
    val remainingTimeMillis: Long = 0L,
    val isTimerActive: Boolean = false,
    val isBlocked: Boolean = false
)

data class TimerOption(
    val durationMinutes: Int,
    val displayText: String,
    val isSelected: Boolean = false
) {
    companion object {
        fun getDefaultOptions(): List<TimerOption> {
            return listOf(
                TimerOption(5, "5 minutes"),
                TimerOption(10, "10 minutes")
            )
        }
    }
}
