package com.rewardtimer.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rewardtimer.app.data.model.TimedApp

@Dao
interface TimedAppDao {
    
    @Query("SELECT * FROM timed_apps WHERE isActive = 1")
    fun getAllActiveTimedApps(): LiveData<List<TimedApp>>
    
    @Query("SELECT * FROM timed_apps")
    fun getAllTimedApps(): LiveData<List<TimedApp>>
    
    @Query("SELECT * FROM timed_apps WHERE packageName = :packageName")
    suspend fun getTimedApp(packageName: String): TimedApp?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimedApp(timedApp: TimedApp)
    
    @Update
    suspend fun updateTimedApp(timedApp: TimedApp)
    
    @Delete
    suspend fun deleteTimedApp(timedApp: TimedApp)
    
    @Query("UPDATE timed_apps SET isActive = :isActive WHERE packageName = :packageName")
    suspend fun updateAppActiveStatus(packageName: String, isActive: Boolean)
    
    @Query("UPDATE timed_apps SET lastUsageTime = :timestamp WHERE packageName = :packageName")
    suspend fun updateLastUsageTime(packageName: String, timestamp: Long)
    
    @Query("UPDATE timed_apps SET totalBlockedTime = totalBlockedTime + :additionalTime WHERE packageName = :packageName")
    suspend fun addBlockedTime(packageName: String, additionalTime: Long)
}
