package com.crownedjester.soft.currenciesinfo.representation.settings

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crownedjester.soft.currenciesinfo.databinding.ItemSettingsBinding
import com.crownedjester.soft.currenciesinfo.domain.model.Currency

class SettingsAdapter :
    RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem.charCode == newItem.charCode

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class SettingsViewHolder(val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(currency: Currency) {
            binding.apply {
                textViewCurrencyCode.text = currency.charCode
                textViewCurrencyScalePlusName
                    .text = "${currency.scale} ${currency.name}"

                switchIsTracking.isChecked = currency.isTracking

                switchIsTracking.setOnCheckedChangeListener { _, isChecked ->
                    currency.isTracking = isChecked

                    if (currency.isTracking) {
                        Log.i("Settings Adapter", "${currency.charCode} is tracking now")
                    } else {
                        Log.i("Settings Adapter", "${currency.charCode} remove from tracking")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = ItemSettingsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val currency = differ.currentList[position]

        holder.bind(currency)
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    override fun onViewRecycled(holder: SettingsViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.switchIsTracking.setOnCheckedChangeListener(null)
    }

    fun moveItem(from: Int, to: Int) {
        val list = differ.currentList.toMutableList()

        val fromLocation = list[from]

        list.removeAt(from)

        if (to < from) {

            list.add(to + 1, fromLocation)

        } else {

            list.add(to - 1, fromLocation)

        }

        list.forEachIndexed { i, curr ->
            curr.position = i
        }

        differ.submitList(list)

        differ.currentList.also {
            for (i in 1 until it.size step 2) {

                Log.i(
                    "SettingsAdapter",
                    "${it[i - 1].position} - ${it[i - 1].charCode}; ${it[i].position} - ${it[i].charCode}; "
                )

            }
        }
    }

}