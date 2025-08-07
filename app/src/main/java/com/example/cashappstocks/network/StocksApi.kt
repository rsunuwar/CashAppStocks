package com.example.cashappstocks.network

import com.example.cashappstocks.models.Stocks
import retrofit2.http.GET
import retrofit2.http.Url
import kotlin.jvm.Throws

interface StocksApi {

    /**
     * Method to fetch stocks from the endpoint
     */
    @GET("/cash-homework/cash-stocks-api/portfolio_malformed.json")
    @Throws(Exception::class)
    suspend fun getStocks(): Stocks

    /**
     * Method to fetch stocks from full url
     *
     * examples
     * url: https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio.json
     * empty: https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio_empty.json
     * malformed: https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio_malformed.json
     */
    @GET
    suspend fun getStocks(@Url url: String): Stocks
}