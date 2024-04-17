package com.example.myapplication
import org.junit.Assert.assertEquals
import org.junit.Test

class DataParserTest {
    @Test
    fun testParseBitcoinData() {
        val jsonData = """{"prices":[[1609459200000, 29000.0],[1609545600000, 29400.0]]}"""
        val result = DataParser.parseBitcoinData(jsonData)

        assertEquals(2, result.size)
        assertEquals(29000.0, result[0].price, 0.001)
        assertEquals(Date(1609459200000), result[0].date)
    }
}
