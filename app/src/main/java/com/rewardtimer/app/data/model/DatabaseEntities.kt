package com.rewardtimer.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timed_apps")
data class TimedApp(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val iconPath: String? = null,
    val timerDurationMinutes: Int, // 5 or 10 minutes
    val isActive: Boolean = true,
    val lastUsageTime: Long = 0L,
    val totalBlockedTime: Long = 0L
)

@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: RewardType,
    val description: String,
    val iconResId: Int,
    val unlockedAt: Long,
    val requiredIdleTimeMinutes: Int // 30 or 60 minutes
)

enum class RewardType {
    CAR,
    SUPERCAR,
    MOTORCYCLE,
    TRUCK,
    SPORTS_CAR
}

@Entity(tableName = "usage_sessions")
data class UsageSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val startTime: Long,
    val endTime: Long?,
    val durationMinutes: Int = 0
)

@Entity(tableName = "idle_periods")
data class IdlePeriod(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long?,
    val durationMinutes: Int = 0,
    val rewardEarned: Boolean = false
)
