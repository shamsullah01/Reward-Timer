package com.rewardtimer.app.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rewardtimer.app.data.model.*
import com.rewardtimer.app.data.repository.RewardTimerRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = RewardTimerRepository(
        // Inject DAOs here
    )
    
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile
    
    private val _currentLevel = MutableLiveData<Int>()
    val currentLevel: LiveData<Int> = _currentLevel
    
    private val _xpProgress = MutableLiveData<Float>()
    val xpProgress: LiveData<Float> = _xpProgress
    
    private val _dailyChallenge = MutableLiveData<DailyChallenge>()
    val dailyChallenge: LiveData<DailyChallenge> = _dailyChallenge
    
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements
    
    private val _powerUps = MutableLiveData<List<PowerUp>>()
    val powerUps: LiveData<List<PowerUp>> = _powerUps
    
    private val _motivationalMessage = MutableLiveData<String>()
    val motivationalMessage: LiveData<String> = _motivationalMessage
    
    private val _showLevelUpAnimation = MutableLiveData<Boolean>()
    val showLevelUpAnimation: LiveData<Boolean> = _showLevelUpAnimation
    
    private val _showAchievementUnlock = MutableLiveData<Achievement?>()
    val showAchievementUnlock: LiveData<Achievement?> = _showAchievementUnlock
    
    init {
        loadUserProfile()
        loadDailyChallenge()
        loadAchievements()
        loadPowerUps()
        updateMotivationalMessage()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            // Load from repository
            val profile = UserProfile(
                level = 5,
                totalXP = 2450,
                currentStreak = 7,
                longestStreak = 12,
                totalFocusMinutes = 840,
                vehiclesUnlocked = 3,
                achievementsUnlocked = 8,
                rank = FocusRank.WARRIOR
            )
            _userProfile.value = profile
            _currentLevel.value = profile.level
            updateXPProgress(profile)
        }
    }
    
    private fun updateXPProgress(profile: UserProfile) {
        val currentLevelXP = calculateLevelXP(profile.level)
        val nextLevelXP = calculateLevelXP(profile.level + 1)
        val progressXP = profile.totalXP - currentLevelXP
        val levelXPRange = nextLevelXP - currentLevelXP
        
        _xpProgress.value = (progressXP.toFloat() / levelXPRange.toFloat()).coerceIn(0f, 1f)
    }
    
    private fun calculateLevelXP(level: Int): Long {
        return (level * level * 100L) + (level * 50L)
    }
    
    private fun loadDailyChallenge() {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val challenge = DailyChallenge(
                date = today,
                challengeType = ChallengeType.FOCUS_MINUTES,
                title = "⚡ Daily Focus Challenge",
                description = "Complete 2 hours of focused work today",
                targetValue = 120,
                currentProgress = 45,
                xpReward = 150
            )
            _dailyChallenge.value = challenge
        }
    }
    
    private fun loadAchievements() {
        viewModelScope.launch {
            val achievements = listOf(
                Achievement(
                    title = "First Steps",
                    description = "Complete your first focus session",
                    emoji = "👶",
                    type = AchievementType.SPECIAL_EVENT,
                    requirement = 1,
                    xpReward = 50,
                    isUnlocked = true
                ),
                Achievement(
                    title = "Focus Warrior",
                    description = "Maintain a 7-day focus streak",
                    emoji = "⚔️",
                    type = AchievementType.STREAK,
                    requirement = 7,
                    xpReward = 200,
                    isUnlocked = true
                ),
                Achievement(
                    title = "Social Media Slayer",
                    description = "Avoid social media for 24 hours",
                    emoji = "🗡️",
                    type = AchievementType.APP_AVOIDANCE,
                    requirement = 1440,
                    xpReward = 300,
                    isUnlocked = false
                )
            )
            _achievements.value = achievements
        }
    }
    
    private fun loadPowerUps() {
        viewModelScope.launch {
            val powerUps = listOf(
                PowerUp(type = PowerUpType.SHIELD, quantity = 2),
                PowerUp(type = PowerUpType.BOOST, quantity = 1),
                PowerUp(type = PowerUpType.FREEZE, quantity = 0),
                PowerUp(type = PowerUpType.DOUBLE_REWARDS, quantity = 3)
            )
            _powerUps.value = powerUps
        }
    }
    
    private fun updateMotivationalMessage() {
        val messages = listOf(
            "You've got this! Start your focus journey! 💪",
            "Amazing progress! Keep the momentum going! 🚀",
            "Your focus streak is on fire! Don't break it! 🔥",
            "You're leveling up! Your focus skills are incredible! ⭐",
            "Time to unlock that next vehicle! 🏎️",
            "Focus mode: ACTIVATED! Ready to conquer distractions! ⚡"
        )
        _motivationalMessage.value = messages.random()
    }
    
    fun addXP(amount: Int) {
        viewModelScope.launch {
            val currentProfile = _userProfile.value ?: return@launch
            val newXP = currentProfile.totalXP + amount
            val newLevel = calculateLevel(newXP)
            
            if (newLevel > currentProfile.level) {
                _showLevelUpAnimation.value = true
                checkForNewAchievements(newLevel)
            }
            
            val updatedProfile = currentProfile.copy(
                totalXP = newXP,
                level = newLevel
            )
            _userProfile.value = updatedProfile
            updateXPProgress(updatedProfile)
        }
    }
    
    private fun calculateLevel(totalXP: Long): Int {
        var level = 1
        while (calculateLevelXP(level + 1) <= totalXP) {
            level++
        }
        return level
    }
    
    private fun checkForNewAchievements(newLevel: Int) {
        val levelAchievements = mapOf(
            5 to Achievement(
                title = "Rising Star",
                description = "Reach level 5",
                emoji = "🌟",
                type = AchievementType.LEVEL_MILESTONE,
                requirement = 5,
                xpReward = 250,
                isUnlocked = true,
                unlockedAt = System.currentTimeMillis()
            ),
            10 to Achievement(
                title = "Focus Master",
                description = "Reach level 10",
                emoji = "🎯",
                type = AchievementType.LEVEL_MILESTONE,
                requirement = 10,
                xpReward = 500,
                isUnlocked = true,
                unlockedAt = System.currentTimeMillis()
            )
        )
        
        levelAchievements[newLevel]?.let { achievement ->
            _showAchievementUnlock.value = achievement
        }
    }
    
    fun usePowerUp(powerUpType: PowerUpType) {
        viewModelScope.launch {
            // Implement power-up usage logic
            updateMotivationalMessage()
        }
    }
    
    fun completeFocusSession(durationMinutes: Int, perfectSession: Boolean) {
        val baseXP = durationMinutes * 2
        val bonusXP = if (perfectSession) baseXP / 2 else 0
        addXP(baseXP + bonusXP)
        
        if (perfectSession) {
            updateMotivationalMessage()
        }
    }
    
    fun dismissLevelUpAnimation() {
        _showLevelUpAnimation.value = false
    }
    
    fun dismissAchievementUnlock() {
        _showAchievementUnlock.value = null
    }
}
