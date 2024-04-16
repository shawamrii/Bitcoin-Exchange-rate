package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBitcoinPriceBinding
import java.text.SimpleDateFormat
import java.util.*

class BitcoinPriceAdapter(private val currentCurrency: String) : ListAdapter<BitcoinPrice, BitcoinPriceAdapter.BitcoinPriceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitcoinPriceViewHolder {
        val binding = ItemBitcoinPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BitcoinPriceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BitcoinPriceViewHolder, position: Int) {
        holder.bind(getItem(position), currentCurrency)
    }

    inner class BitcoinPriceViewHolder(private val binding: ItemBitcoinPriceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bitcoinPrice: BitcoinPrice, currency: String) {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            binding.dateText.text = dateFormat.format(bitcoinPrice.date)
            val currencySymbol = if (currency == "eur") "€" else "$"
            binding.priceText.text = String.format(Locale.getDefault(), "%.2f %s", bitcoinPrice.price, currencySymbol)
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<BitcoinPrice>() {
        override fun areItemsTheSame(oldItem: BitcoinPrice, newItem: BitcoinPrice): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: BitcoinPrice, newItem: BitcoinPrice): Boolean {
            return oldItem == newItem
        }
    }
}
