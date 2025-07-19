package com.rewardtimer.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.rewardtimer.app.data.dao.*
import com.rewardtimer.app.data.model.*

@Database(
    entities = [TimedApp::class, Reward::class, UsageSession::class, IdlePeriod::class],
    version = 1,
    exportSchema = false
)
abstract class RewardTimerDatabase : RoomDatabase() {
    
    abstract fun timedAppDao(): TimedAppDao
    abstract fun rewardDao(): RewardDao
    abstract fun usageSessionDao(): UsageSessionDao
    abstract fun idlePeriodDao(): IdlePeriodDao
    
    companion object {
        @Volatile
        private var INSTANCE: RewardTimerDatabase? = null
        
        fun getDatabase(context: Context): RewardTimerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RewardTimerDatabase::class.java,
                    "reward_timer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
