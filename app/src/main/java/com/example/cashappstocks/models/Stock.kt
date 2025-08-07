package com.example.cashappstocks.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Stock(
    val ticker: String,
    val name: String,
    val currency: String,
    @SerializedName("current_price_cents") val currentPriceCents: Int,
    val quantity: Int?,
    @SerializedName("current_price_timestamp") val currentTimestamp: Int
) {

    fun priceInDollars() = currentPriceCents / 100.00

    fun currentDateTime(): String {
        val date = Date(currentTimestamp.toLong())
        val dateFormat = SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }
}

data class Stocks(
    @SerializedName("stocks")
    val stockList: List<Stock>
)

sealed class StocksViewState {

    object UnInitialized : StocksViewState()

    object Loading : StocksViewState()

    data class LoadingError(val errorMessageId: Int) : StocksViewState()

    data class Result(val result: Stocks) : StocksViewState()
}
