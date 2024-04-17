package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class BitcoinPriceAdapterTest {
    @Test
    fun testCurrencySymbol() {
        val bindingMock = mock(ItemBitcoinPriceBinding::class.java)
        val viewHolder = BitcoinPriceAdapter("eur").BitcoinPriceViewHolder(bindingMock)
        val bitcoinPrice = BitcoinPrice(Date(), 12345.67)

        viewHolder.bind(bitcoinPrice, "eur")

        verify(bindingMock.priceText).setText("12345.67 €")
    }
}
