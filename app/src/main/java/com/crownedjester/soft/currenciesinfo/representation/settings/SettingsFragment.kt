package com.crownedjester.soft.currenciesinfo.representation.settings

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.crownedjester.soft.currenciesinfo.R
import com.crownedjester.soft.currenciesinfo.databinding.FragmentSettingsBinding
import com.crownedjester.soft.currenciesinfo.representation.viewmodel.CurrenciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<CurrenciesViewModel>()

    private var settingsAdapter: SettingsAdapter = SettingsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        itemTouchHelper.attachToRecyclerView(binding.rvSettings)

        binding.rvSettings.adapter = settingsAdapter

        lifecycleScope.launch {
            viewModel.todayCurrenciesState.collectLatest { state ->
                if (state.error.isNotBlank()) {
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show()
                } else {
                    settingsAdapter.differ.submitList(state.data)
                }
                Log.i("Settings Fragment", state.data.toString())

            }
        }

        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_bar_settings, menu)

        val acceptChangesBtn = menu.findItem(R.id.accept_changes)

        acceptChangesBtn.icon.setTint(Color.WHITE)

        acceptChangesBtn.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_dashboardFragment)

            viewModel.saveCache(settingsAdapter.differ.currentList)

            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : SimpleCallback(UP or DOWN, 0) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as SettingsAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                adapter.moveItem(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                return
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)

    }

}