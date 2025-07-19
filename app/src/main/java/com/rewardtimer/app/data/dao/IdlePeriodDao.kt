package com.rewardtimer.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rewardtimer.app.data.model.IdlePeriod

@Dao
interface IdlePeriodDao {
    
    @Query("SELECT * FROM idle_periods ORDER BY startTime DESC")
    fun getAllIdlePeriods(): LiveData<List<IdlePeriod>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdlePeriod(idlePeriod: IdlePeriod): Long
    
    @Update
    suspend fun updateIdlePeriod(idlePeriod: IdlePeriod)
    
    @Query("SELECT * FROM idle_periods WHERE endTime IS NULL")
    suspend fun getActiveIdlePeriod(): IdlePeriod?
    
    @Query("UPDATE idle_periods SET endTime = :endTime, durationMinutes = :duration WHERE id = :periodId")
    suspend fun endIdlePeriod(periodId: Long, endTime: Long, duration: Int)
    
    @Query("UPDATE idle_periods SET rewardEarned = 1 WHERE id = :periodId")
    suspend fun markRewardEarned(periodId: Long)
    
    @Query("SELECT SUM(durationMinutes) FROM idle_periods WHERE rewardEarned = 0 AND endTime IS NOT NULL")
    suspend fun getTotalUnrewardedIdleTime(): Int?
}
