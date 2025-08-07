package com.example.cashappstocks

import com.example.cashappstocks.models.Stock
import com.example.cashappstocks.models.Stocks
import com.example.cashappstocks.models.StocksViewState
import com.example.cashappstocks.network.StocksApi
import com.example.cashappstocks.ui.screens.StocksListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class StocksViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private var dispatcher = UnconfinedTestDispatcher()
    private val stocksApi: StocksApi = mock()

    private lateinit var stocksListVM: StocksListViewModel

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        stocksListVM = StocksListViewModel(stocksApi, dispatcher)
    }

    @Test
    fun has_correctly_initial_state() {
        assertEquals(StocksViewState.UnInitialized, stocksListVM.stockViewStateFlow.value)
    }

    @Test
    fun loads_stocks_correctly_when_data_is_returned() = runTest {
        val testResponse = Stocks(
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
                )
            )
        )
        whenever(stocksApi.getStocks()).thenReturn(testResponse)

        stocksListVM.loadStocks()

        assertEquals(StocksViewState.Result(testResponse), stocksListVM.stockViewStateFlow.value)
    }

    @Test
    fun loads_stocks_correctly_when_emptyList_is_returned() = runTest {
        val emptyResponse = Stocks(emptyList())
        whenever(stocksApi.getStocks()).thenReturn(emptyResponse)

        stocksListVM.loadStocks()

        assertEquals(StocksViewState.Result(emptyResponse), stocksListVM.stockViewStateFlow.value)
    }

    @Test
    fun loads_stocks_correctly_when_there_is_error_fetching_from_network() = runTest {

        whenever(stocksApi.getStocks())
            .thenThrow(Exception("Error"))
        stocksListVM.loadStocks()

        assertEquals(
            StocksViewState.LoadingError(R.string.error_loading),
            stocksListVM.stockViewStateFlow.value
        )
    }
}
