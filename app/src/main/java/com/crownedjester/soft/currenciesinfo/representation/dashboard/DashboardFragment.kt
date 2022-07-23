package com.crownedjester.soft.currenciesinfo.representation.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.crownedjester.soft.currenciesinfo.R
import com.crownedjester.soft.currenciesinfo.databinding.FragmentDashboardBinding
import com.crownedjester.soft.currenciesinfo.representation.viewmodel.CurrenciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private val viewModel by activityViewModels<CurrenciesViewModel>()
    private val dashboardAdapter: CurrencyDashboardAdapter = CurrencyDashboardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        binding!!.rvDashboard.adapter = dashboardAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.todayCurrenciesState.collectLatest { state ->
                    dashboardAdapter.differ.submitList(state.data)
                    if (state.error.isNotBlank()) {
                        Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        setHasOptionsMenu(true)
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_bar_dashboard, menu)
        val toSettingsBtn = menu.findItem(R.id.toSettings)
        with(toSettingsBtn) {
            icon.setTint(Color.WHITE)

            isVisible = viewModel.todayCurrenciesState.value.error.isBlank()

            setOnMenuItemClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
                false
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}