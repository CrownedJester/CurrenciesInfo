package com.crownedjester.soft.currenciesinfo.representation.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crownedjester.soft.currenciesinfo.databinding.ItemDashboardBinding
import com.crownedjester.soft.currenciesinfo.domain.model.Currency

class CurrencyDashboardAdapter :
    RecyclerView.Adapter<CurrencyDashboardAdapter.DashboardItemViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem.numCode == newItem.numCode

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    class DashboardItemViewHolder(private val binding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(currency: Currency) {
            binding.apply {

                textViewCurrencyCode.text = currency.charCode
                textViewCurrencyScalePlusName.text = "${currency.scale} ${currency.name}"
                textViewCurrencyRateToday.text = currency.rate.toString()
                textViewCurrencyRateAlternative.text = currency.alternativeRate.toString()

                itemView.visibility = if (currency.isTracking) View.VISIBLE else View.GONE
                itemView.layoutParams = if (currency.isTracking)
                    RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                else
                    RecyclerView.LayoutParams(0, 0)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardItemViewHolder {
        val binding =
            ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DashboardItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardItemViewHolder, position: Int) {
        val currency = differ.currentList[position]

        holder.bind(currency)
    }

    override fun getItemCount(): Int =
        differ.currentList.size

}