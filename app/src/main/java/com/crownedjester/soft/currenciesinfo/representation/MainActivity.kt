package com.crownedjester.soft.currenciesinfo.representation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.crownedjester.soft.currenciesinfo.R
import com.crownedjester.soft.currenciesinfo.databinding.ActivityMainBinding
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.MODE_DEPLOY
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.TOMORROW_CODE
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.YESTERDAY_CODE
import com.crownedjester.soft.currenciesinfo.representation.viewmodel.CurrenciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CurrenciesViewModel>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHost.navController

        binding.apply {

            textViewTodayDate.text = DateUtil.getCurrentDate(MODE_DEPLOY)

            lifecycleScope.launchWhenCreated {

                viewModel.isTomorrowsDataExistsStateFlow.collectLatest {
                    this@apply.textViewAlternativeDate.text =
                        if (it) DateUtil.getAlternativeDate(
                            MODE_DEPLOY, TOMORROW_CODE
                        ) else DateUtil.getAlternativeDate(MODE_DEPLOY, YESTERDAY_CODE)
                }
            }
            actionBarMain.apply {
                val currentDestinationTitle =
                    this.findViewById<TextView>(R.id.text_view_current_destination)


                navController.addOnDestinationChangedListener { _, destination, _ ->
                    this.title = ""
                    currentDestinationTitle.text = destination.label

                    if (destination.id == R.id.settingsFragment) {
                        textViewTodayDate.visibility = View.INVISIBLE
                        textViewAlternativeDate.visibility = View.INVISIBLE
                    } else {
                        textViewTodayDate.visibility = View.VISIBLE
                        textViewAlternativeDate.visibility = View.VISIBLE
                    }
                }
            }
        }

        setSupportActionBar(binding.actionBarMain)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val navController = findNavController(R.id.fragment_container_view)
            if (navController.currentDestination?.id == R.id.settingsFragment) {
                Log.i("MainActivity", "Navigate to dashboard")
                navController.navigate(R.id.action_settingsFragment_to_dashboardFragment)
            }
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}