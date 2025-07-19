package com.rewardtimer.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rewardtimer.app.data.model.Reward

@Dao
interface RewardDao {
    
    @Query("SELECT * FROM rewards ORDER BY unlockedAt DESC")
    fun getAllRewards(): LiveData<List<Reward>>
    
    @Query("SELECT * FROM rewards WHERE id = :rewardId")
    suspend fun getReward(rewardId: Long): Reward?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: Reward): Long
    
    @Update
    suspend fun updateReward(reward: Reward)
    
    @Delete
    suspend fun deleteReward(reward: Reward)
    
    @Query("SELECT COUNT(*) FROM rewards")
    suspend fun getRewardCount(): Int
}
