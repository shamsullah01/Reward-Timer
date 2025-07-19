package com.rewardtimer.app.data.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import com.rewardtimer.app.data.dao.*
import com.rewardtimer.app.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RewardTimerRepository(
    private val timedAppDao: TimedAppDao,
    private val rewardDao: RewardDao,
    private val usageSessionDao: UsageSessionDao,
    private val idlePeriodDao: IdlePeriodDao,
    private val context: Context
) {

    // Timed Apps
    fun getAllActiveTimedApps(): LiveData<List<TimedApp>> = timedAppDao.getAllActiveTimedApps()
    
    fun getAllTimedApps(): LiveData<List<TimedApp>> = timedAppDao.getAllTimedApps()
    
    suspend fun insertTimedApp(timedApp: TimedApp) = timedAppDao.insertTimedApp(timedApp)
    
    suspend fun updateTimedApp(timedApp: TimedApp) = timedAppDao.updateTimedApp(timedApp)
    
    suspend fun deleteTimedApp(timedApp: TimedApp) = timedAppDao.deleteTimedApp(timedApp)
    
    suspend fun getTimedApp(packageName: String): TimedApp? = timedAppDao.getTimedApp(packageName)
    
    // Rewards
    fun getAllRewards(): LiveData<List<Reward>> = rewardDao.getAllRewards()
    
    suspend fun insertReward(reward: Reward): Long = rewardDao.insertReward(reward)
    
    suspend fun getRewardCount(): Int = rewardDao.getRewardCount()
    
    // Usage Sessions
    fun getAllUsageSessions(): LiveData<List<UsageSession>> = usageSessionDao.getAllUsageSessions()
    
    suspend fun insertUsageSession(session: UsageSession): Long = usageSessionDao.insertUsageSession(session)
    
    suspend fun endUsageSession(sessionId: Long, endTime: Long, duration: Int) = 
        usageSessionDao.endUsageSession(sessionId, endTime, duration)
    
    // Idle Periods
    fun getAllIdlePeriods(): LiveData<List<IdlePeriod>> = idlePeriodDao.getAllIdlePeriods()
    
    suspend fun insertIdlePeriod(idlePeriod: IdlePeriod): Long = idlePeriodDao.insertIdlePeriod(idlePeriod)
    
    suspend fun getActiveIdlePeriod(): IdlePeriod? = idlePeriodDao.getActiveIdlePeriod()
    
    suspend fun endIdlePeriod(periodId: Long, endTime: Long, duration: Int) = 
        idlePeriodDao.endIdlePeriod(periodId, endTime, duration)
    
    // Get installed apps
    suspend fun getInstalledApps(): List<InstalledApp> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val installedPackages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        
        installedPackages
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Filter out system apps
            .filter { it.packageName != context.packageName } // Filter out our own app
            .map { appInfo ->
                InstalledApp(
                    packageName = appInfo.packageName,
                    appName = packageManager.getApplicationLabel(appInfo).toString(),
                    icon = packageManager.getApplicationIcon(appInfo),
                    isSystemApp = false
                )
            }
            .sortedBy { it.appName }
    }
}
