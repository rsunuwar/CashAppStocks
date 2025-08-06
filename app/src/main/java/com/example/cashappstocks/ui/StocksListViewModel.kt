package com.example.cashappstocks.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashappstocks.models.Stock
import com.example.cashappstocks.models.Stocks
import com.example.cashappstocks.network.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StocksListViewModel : ViewModel() {

    companion object {
        val TAG: String = StocksListViewModel::javaClass.name
    }

    private val _stockListFlow = MutableStateFlow(Stocks(emptyList()))
    val stockListFlow: StateFlow<Stocks> = _stockListFlow.asStateFlow()

    private val stocksApi = NetworkRequest.getStocksApi()

    init {
        loadStocks()
    }

    fun loadStocks() {
        viewModelScope.launch {
            try {
                _stockListFlow.value = stocksApi.getStocks()

            } catch (e: Exception) {
                _stockListFlow.value = Stocks(emptyList())
                val message = e.message.toString()
                Log.e(TAG, "Error loading stocks, error = $message")
            }
        }
    }
}

val testData = Stocks(
    listOf(
        Stock(
            ticker = "^GSPC",
            name = "S&P 500",
            currency = "USD",
            currentPriceCents = 318157,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "RUNINC",
            name = "Runners Inc.",
            currency = "USD",
            currentPriceCents = 3614,
            quantity = 5,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "BAC",
            name = "Bank of America Corporation",
            currency = "USD",
            currentPriceCents = 2393,
            quantity = 10,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "EXPE",
            name = "Expedia Group, Inc.",
            currency = "USD",
            currentPriceCents = 8165,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "GRUB",
            name = "Grubhub Inc.",
            currency = "USD",
            currentPriceCents = 6975,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "TRUNK",
            name = "Trunk Club",
            currency = "USD",
            currentPriceCents = 17632,
            quantity = 9,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "FIT",
            name = "Fitbit, Inc.",
            currency = "USD",
            currentPriceCents = 678,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "UA",
            name = "Under Armour, Inc.",
            currency = "USD",
            currentPriceCents = 844,
            quantity = 7,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "VTI",
            name = "Vanguard Total Stock Market Index Fund ETF Shares",
            currency = "USD",
            currentPriceCents = 15994,
            quantity = 1,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "RUN",
            name = "Run",
            currency = "USD",
            currentPriceCents = 6720,
            quantity = 12,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "VWO",
            name = "Vanguard FTSE Emerging Markets",
            currency = "USD",
            currentPriceCents = 4283,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "JNJ",
            name = "Johnson & Johnson",
            currency = "USD",
            currentPriceCents = 14740,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "BRKA",
            name = "Berkshire Hathaway Inc.",
            currency = "USD",
            currentPriceCents = 28100000,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "^DJI",
            name = "Dow Jones Industrial Average",
            currency = "USD",
            currentPriceCents = 2648154,
            quantity = 5,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "^TNX",
            name = "Treasury Yield 10 Years",
            currency = "USD",
            currentPriceCents = 61,
            quantity = null,
            currentTimestamp = 1681845832
        ),
        Stock(
            ticker = "RUNWAY",
            name = "Rent The Runway",
            currency = "USD",
            currentPriceCents = 24819,
            quantity = 20,
            currentTimestamp = 1681845832
        )
    )
)
