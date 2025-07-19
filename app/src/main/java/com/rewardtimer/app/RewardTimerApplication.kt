package com.rewardtimer.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.room.Room
import com.rewardtimer.app.data.database.RewardTimerDatabase

class RewardTimerApplication : Application() {

    companion object {
        const val TIMER_NOTIFICATION_CHANNEL_ID = "timer_notifications"
        const val REWARD_NOTIFICATION_CHANNEL_ID = "reward_notifications"
        const val MONITORING_NOTIFICATION_CHANNEL_ID = "monitoring_notifications"
    }

    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            RewardTimerDatabase::class.java,
            "reward_timer_database"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Timer notifications channel
            val timerChannel = NotificationChannel(
                TIMER_NOTIFICATION_CHANNEL_ID,
                "Timer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for timer expiration and app blocking"
            }

            // Reward notifications channel
            val rewardChannel = NotificationChannel(
                REWARD_NOTIFICATION_CHANNEL_ID,
                "Reward Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for earned rewards"
            }

            // Monitoring service channel
            val monitoringChannel = NotificationChannel(
                MONITORING_NOTIFICATION_CHANNEL_ID,
                "Background Monitoring",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background service for app monitoring"
            }

            notificationManager.createNotificationChannels(
                listOf(timerChannel, rewardChannel, monitoringChannel)
            )
        }
    }
}
