package com.rewardtimer.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rewardtimer.app.service.AppMonitoringService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            
            // Restart monitoring service after boot
            val serviceIntent = Intent(context, AppMonitoringService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}
