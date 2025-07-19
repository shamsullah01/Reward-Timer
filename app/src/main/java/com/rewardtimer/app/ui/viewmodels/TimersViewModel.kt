package com.rewardtimer.app.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rewardtimer.app.data.database.RewardTimerDatabase
import com.rewardtimer.app.data.model.InstalledApp
import com.rewardtimer.app.data.model.TimedApp
import com.rewardtimer.app.data.repository.RewardTimerRepository
import kotlinx.coroutines.launch

class TimersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RewardTimerRepository
    private val _installedApps = MutableLiveData<List<InstalledApp>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _selectedAppsCount = MutableLiveData<Int>()

    val installedApps: LiveData<List<InstalledApp>> = _installedApps
    val isLoading: LiveData<Boolean> = _isLoading
    val selectedAppsCount: LiveData<Int> = _selectedAppsCount
    val timedApps: LiveData<List<TimedApp>>

    init {
        val database = RewardTimerDatabase.getDatabase(application)
        repository = RewardTimerRepository(
            database.timedAppDao(),
            database.rewardDao(),
            database.usageSessionDao(),
            database.idlePeriodDao(),
            application
        )
        timedApps = repository.getAllActiveTimedApps()
        loadInstalledApps()
    }

    fun loadInstalledApps() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val apps = repository.getInstalledApps()
                val timedAppsMap = repository.getAllTimedApps().value?.associateBy { it.packageName } ?: emptyMap()
                
                val appsWithTimerState = apps.map { app ->
                    val timedApp = timedAppsMap[app.packageName]
                    app.copy(
                        isSelected = timedApp?.isActive == true,
                        timerDurationMinutes = timedApp?.timerDurationMinutes ?: 5,
                        isTimerActive = timedApp != null && isAppTimerActive(timedApp)
                    )
                }
                
                _installedApps.value = appsWithTimerState
                updateSelectedAppsCount(appsWithTimerState)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                // Handle error
            }
        }
    }

    fun updateAppSelection(app: InstalledApp, isSelected: Boolean) {
        val currentApps = _installedApps.value?.toMutableList() ?: return
        val index = currentApps.indexOfFirst { it.packageName == app.packageName }
        
        if (index != -1) {
            currentApps[index] = app.copy(isSelected = isSelected)
            _installedApps.value = currentApps
            updateSelectedAppsCount(currentApps)
        }
    }

    fun updateTimerDuration(app: InstalledApp, durationMinutes: Int) {
        val currentApps = _installedApps.value?.toMutableList() ?: return
        val index = currentApps.indexOfFirst { it.packageName == app.packageName }
        
        if (index != -1) {
            currentApps[index] = app.copy(timerDurationMinutes = durationMinutes)
            _installedApps.value = currentApps
        }
    }

    fun startTimersForSelectedApps() {
        val selectedApps = _installedApps.value?.filter { it.isSelected } ?: return
        
        viewModelScope.launch {
            selectedApps.forEach { app ->
                val timedApp = TimedApp(
                    packageName = app.packageName,
                    appName = app.appName,
                    timerDurationMinutes = app.timerDurationMinutes,
                    isActive = true,
                    lastUsageTime = System.currentTimeMillis()
                )
                repository.insertTimedApp(timedApp)
            }
            // Restart the monitoring service
            // TODO: Start or restart AppMonitoringService
        }
    }

    private fun updateSelectedAppsCount(apps: List<InstalledApp>) {
        _selectedAppsCount.value = apps.count { it.isSelected }
    }

    private fun isAppTimerActive(timedApp: TimedApp): Boolean {
        val currentTime = System.currentTimeMillis()
        val timerDurationMillis = timedApp.timerDurationMinutes * 60 * 1000L
        return (currentTime - timedApp.lastUsageTime) < timerDurationMillis
    }
}
