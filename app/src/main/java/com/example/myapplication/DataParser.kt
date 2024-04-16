package com.example.myapplication

import org.json.JSONObject
import java.util.Date


// DataParser object
object DataParser {
    fun parseBitcoinData(jsonData: String): List<BitcoinPrice> {
        val jsonObject = JSONObject(jsonData)
        val pricesArray = jsonObject.getJSONArray("prices")
        val priceList = mutableListOf<BitcoinPrice>()
        for (i in 0 until pricesArray.length()) {
            val singleDayData = pricesArray.getJSONArray(i)
            val timestamp = singleDayData.getLong(0) // Get the timestamp
            val price = singleDayData.getDouble(1) // Get the price
            val date = Date(timestamp) // Convert the timestamp to a Date object
            priceList.add(BitcoinPrice(date, price))
        }
        return priceList
    }
}
