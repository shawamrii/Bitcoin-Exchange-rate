package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BitcoinPriceAdapter
    private var currentCurrency = "eur" // Default currency
    private var currentSorting = "Newest First" // Default sorting option

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSortingSpinner()
        setupCurrencySpinner()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = BitcoinPriceAdapter(currentCurrency) // Pass currentCurrency here
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSortingSpinner() {
        val sortingOptions = arrayOf("Newest First", "Oldest First")

        val sortingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortingOptions)
        sortingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortingSpinner.adapter = sortingAdapter

        binding.sortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentSorting = sortingOptions[position]
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }
    }

    private fun setupCurrencySpinner() {
        val currencyOptions = arrayOf("EUR", "USD")
        val currencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyOptions)
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.currencySpinner.adapter = currencyAdapter

        binding.currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCurrency = if (position == 0) "eur" else "usd"
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }
    }

    private fun loadData() {
        NetworkUtils.fetchBitcoinPriceData(currentCurrency) { jsonData ->
            runOnUiThread {
                if (jsonData.isNotEmpty()) {
                    val priceList = DataParser.parseBitcoinData(jsonData)
                    val sortedList = sortBitcoinPrices(priceList)
                    adapter.submitList(sortedList)
                } else {
                    showToast("No data available")
                }
            }
        }
    }

    private fun sortBitcoinPrices(priceList: List<BitcoinPrice>): List<BitcoinPrice> {
        return when (currentSorting) {
            "Newest First" -> priceList.sortedByDescending { it.date }
            "Oldest First" -> priceList.sortedBy { it.date }
            else -> priceList
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
