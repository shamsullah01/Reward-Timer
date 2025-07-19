package com.rewardtimer.app.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Intent
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.rewardtimer.app.R
import com.rewardtimer.app.data.database.RewardTimerDatabase
import kotlinx.coroutines.launch

class AppBlockingAccessibilityService : AccessibilityService() {

    private var overlayView: android.view.View? = null
    private var windowManager: WindowManager? = null
    private lateinit var database: RewardTimerDatabase

    override fun onServiceConnected() {
        super.onServiceConnected()
        
        database = RewardTimerDatabase.getDatabase(this)
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        
        // Configure accessibility service
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
            packageNames = null // Monitor all packages
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            if (packageName != null && packageName != this.packageName) {
                checkAndBlockApp(packageName)
            }
        }
    }

    private fun checkAndBlockApp(packageName: String) {
        // Check if this app should be blocked
        // This is a simplified version - you'd implement actual timer checking here
        // For now, we'll just create a placeholder
        
        // In a real implementation, you would:
        // 1. Check if the app is in the timed apps list
        // 2. Check if the timer has expired
        // 3. Show overlay if app should be blocked
        
        // Placeholder logic - remove this and implement actual checking
        if (packageName.contains("instagram") || packageName.contains("facebook")) {
            showBlockingOverlay()
        }
    }

    private fun showBlockingOverlay() {
        if (overlayView != null) return // Already showing
        
        val layoutInflater = LayoutInflater.from(this)
        overlayView = layoutInflater.inflate(R.layout.overlay_app_blocked, null)
        
        val layoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
            gravity = Gravity.CENTER
        }
        
        overlayView?.findViewById<Button>(R.id.btn_close_overlay)?.setOnClickListener {
            hideBlockingOverlay()
            // Navigate back to home screen
            val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(homeIntent)
        }
        
        try {
            windowManager?.addView(overlayView, layoutParams)
        } catch (e: Exception) {
            // Handle overlay permission not granted
        }
    }

    private fun hideBlockingOverlay() {
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
                overlayView = null
            } catch (e: Exception) {
                // Handle removal error
            }
        }
    }

    override fun onInterrupt() {
        hideBlockingOverlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideBlockingOverlay()
    }
}
