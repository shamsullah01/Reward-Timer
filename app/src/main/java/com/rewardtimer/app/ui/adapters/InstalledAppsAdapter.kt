package com.rewardtimer.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rewardtimer.app.R
import com.rewardtimer.app.data.model.InstalledApp
import com.rewardtimer.app.databinding.ItemInstalledAppBinding
import java.util.concurrent.TimeUnit

class InstalledAppsAdapter(
    private val onAppSelectionChanged: (InstalledApp, Boolean) -> Unit,
    private val onTimerDurationChanged: (InstalledApp, Int) -> Unit
) : ListAdapter<InstalledApp, InstalledAppsAdapter.ViewHolder>(AppDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInstalledAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemInstalledAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(app: InstalledApp) {
            binding.apply {
                // Set app info
                tvAppName.text = app.appName
                tvPackageName.text = app.packageName
                ivAppIcon.setImageDrawable(app.icon)

                // Set selection state
                switchAppSelected.isChecked = app.isSelected
                switchAppSelected.setOnCheckedChangeListener { _, isChecked ->
                    onAppSelectionChanged(app, isChecked)
                }

                // Show/hide timer options based on selection
                llTimerOptions.visibility = if (app.isSelected) View.VISIBLE else View.GONE
                llTimerStatus.visibility = if (app.isTimerActive) View.VISIBLE else View.GONE

                // Set timer duration toggle
                when (app.timerDurationMinutes) {
                    5 -> toggleTimerDuration.check(R.id.btn_5_minutes)
                    10 -> toggleTimerDuration.check(R.id.btn_10_minutes)
                }

                // Timer duration change listener
                toggleTimerDuration.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        val duration = when (checkedId) {
                            R.id.btn_5_minutes -> 5
                            R.id.btn_10_minutes -> 10
                            else -> 5
                        }
                        onTimerDurationChanged(app, duration)
                    }
                }

                // Update timer status if active
                if (app.isTimerActive) {
                    updateTimerDisplay(app)
                }
            }
        }

        private fun updateTimerDisplay(app: InstalledApp) {
            binding.apply {
                val totalTimeMillis = app.timerDurationMinutes * 60 * 1000L
                val progress = ((totalTimeMillis - app.remainingTimeMillis).toFloat() / totalTimeMillis * 100).toInt()
                
                progressTimer.progress = progress
                
                val minutes = TimeUnit.MILLISECONDS.toMinutes(app.remainingTimeMillis)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(app.remainingTimeMillis) % 60
                tvRemainingTime.text = root.context.getString(
                    R.string.time_remaining_format,
                    minutes,
                    seconds
                )
            }
        }
    }

    class AppDiffCallback : DiffUtil.ItemCallback<InstalledApp>() {
        override fun areItemsTheSame(oldItem: InstalledApp, newItem: InstalledApp): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: InstalledApp, newItem: InstalledApp): Boolean {
            return oldItem == newItem
        }
    }
}
