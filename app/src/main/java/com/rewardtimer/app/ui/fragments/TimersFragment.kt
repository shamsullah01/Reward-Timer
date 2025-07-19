package com.rewardtimer.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rewardtimer.app.databinding.FragmentTimersBinding
import com.rewardtimer.app.ui.adapters.InstalledAppsAdapter
import com.rewardtimer.app.ui.viewmodels.TimersViewModel

class TimersFragment : Fragment() {

    private var _binding: FragmentTimersBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TimersViewModel by viewModels()
    private lateinit var appsAdapter: InstalledAppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        appsAdapter = InstalledAppsAdapter(
            onAppSelectionChanged = { app, isSelected ->
                viewModel.updateAppSelection(app, isSelected)
            },
            onTimerDurationChanged = { app, duration ->
                viewModel.updateTimerDuration(app, duration)
            }
        )

        binding.rvInstalledApps.apply {
            adapter = appsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.installedApps.observe(viewLifecycleOwner) { apps ->
            appsAdapter.submitList(apps)
            binding.tvEmptyState.visibility = if (apps.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.selectedAppsCount.observe(viewLifecycleOwner) { count ->
            binding.btnStartTimers.isEnabled = count > 0
            binding.btnStartTimers.text = if (count > 0) {
                "Start Timers ($count apps selected)"
            } else {
                "Start Timers"
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnStartTimers.setOnClickListener {
            viewModel.startTimersForSelectedApps()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
