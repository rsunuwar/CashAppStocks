package com.example.cashappstocks.models

// sealed class representing different states for loading stocks from network
sealed class StocksViewState {

    object UnInitialized : StocksViewState()

    object Loading : StocksViewState()

    data class LoadingError(val errorMessageId: Int) : StocksViewState()

    data class Result(val result: Stocks) : StocksViewState()
}
