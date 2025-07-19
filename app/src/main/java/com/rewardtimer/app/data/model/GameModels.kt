package com.rewardtimer.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Long = 1,
    val username: String = "Focus Hero",
    val level: Int = 1,
    val totalXP: Long = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalFocusMinutes: Long = 0,
    val vehiclesUnlocked: Int = 0,
    val achievementsUnlocked: Int = 0,
    val rank: FocusRank = FocusRank.ROOKIE,
    val lastActiveDate: Long = System.currentTimeMillis()
)

enum class FocusRank(val displayName: String, val emoji: String, val requiredLevel: Int) {
    ROOKIE("Focus Rookie", "🥉", 1),
    WARRIOR("Focus Warrior", "🥈", 5),
    MASTER("Focus Master", "🥇", 15),
    LEGEND("Focus Legend", "💎", 30),
    MYTHIC("Mythic Focus", "👑", 50)
}

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val emoji: String,
    val type: AchievementType,
    val requirement: Int,
    val xpReward: Int,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

enum class AchievementType {
    STREAK,
    TOTAL_FOCUS_TIME,
    DAILY_GOALS,
    APP_AVOIDANCE,
    LEVEL_MILESTONE,
    SPECIAL_EVENT
}

@Entity(tableName = "daily_challenges")
data class DailyChallenge(
    @PrimaryKey
    val date: String, // yyyy-MM-dd format
    val challengeType: ChallengeType,
    val title: String,
    val description: String,
    val targetValue: Int,
    val currentProgress: Int = 0,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)

enum class ChallengeType {
    FOCUS_MINUTES,
    APP_AVOIDANCE,
    STREAK_MAINTAIN,
    EARLY_BIRD,
    NIGHT_OWL,
    WEEKEND_WARRIOR
}

@Entity(tableName = "power_ups")
data class PowerUp(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: PowerUpType,
    val quantity: Int = 0,
    val lastUsed: Long? = null
)

enum class PowerUpType(val displayName: String, val emoji: String, val description: String) {
    SHIELD("Distraction Shield", "🛡️", "Blocks all notifications for 30 minutes"),
    BOOST("Focus Boost", "⚡", "Doubles XP for next focus session"),
    FREEZE("Time Freeze", "❄️", "Extends timer by 15 minutes"),
    DOUBLE_REWARDS("Double Rewards", "💰", "2x vehicle unlock progress")
}

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long? = null,
    val plannedDurationMinutes: Int,
    val actualDurationMinutes: Int = 0,
    val appsBlocked: List<String> = emptyList(),
    val distractionsCount: Int = 0,
    val xpEarned: Int = 0,
    val perfectSession: Boolean = false,
    val powerUpsUsed: List<PowerUpType> = emptyList()
)

data class VehicleReward(
    val id: Long,
    val name: String,
    val emoji: String,
    val category: VehicleCategory,
    val requiredLevel: Int,
    val requiredFocusHours: Int,
    val rarity: VehicleRarity,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val description: String
)

enum class VehicleCategory {
    BIKE,
    MOTORCYCLE,
    CAR,
    TRUCK,
    BOAT,
    PLANE,
    ROCKET,
    SPECIAL
}

enum class VehicleRarity(val color: String, val sparkle: String) {
    COMMON("#4CAF50", "✨"),
    RARE("#2196F3", "🌟"),
    EPIC("#9C27B0", "💫"),
    LEGENDARY("#FF9800", "⭐"),
    MYTHIC("#F44336", "🔥")
}
