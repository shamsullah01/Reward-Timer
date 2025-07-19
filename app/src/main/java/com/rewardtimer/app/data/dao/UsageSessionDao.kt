package com.rewardtimer.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rewardtimer.app.data.model.UsageSession

@Dao
interface UsageSessionDao {
    
    @Query("SELECT * FROM usage_sessions ORDER BY startTime DESC")
    fun getAllUsageSessions(): LiveData<List<UsageSession>>
    
    @Query("SELECT * FROM usage_sessions WHERE packageName = :packageName ORDER BY startTime DESC")
    fun getUsageSessionsForApp(packageName: String): LiveData<List<UsageSession>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsageSession(session: UsageSession): Long
    
    @Update
    suspend fun updateUsageSession(session: UsageSession)
    
    @Query("SELECT * FROM usage_sessions WHERE endTime IS NULL")
    suspend fun getActiveUsageSessions(): List<UsageSession>
    
    @Query("UPDATE usage_sessions SET endTime = :endTime, durationMinutes = :duration WHERE id = :sessionId")
    suspend fun endUsageSession(sessionId: Long, endTime: Long, duration: Int)
}
