package com.example.cashappstocks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cashappstocks.R
import com.example.cashappstocks.models.Stocks
import com.example.cashappstocks.models.StocksViewState
import com.example.cashappstocks.ui.theme.CashAppStocksTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StocksListScreen(viewModel: StocksListViewModel) {

    val stocksViewState by viewModel.stockViewStateFlow.collectAsStateWithLifecycle()
//    val stocksViewState = StocksViewState.Result(testData) // for testing without actual network request

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.cash_app_stocks),
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    TextButton(
                        modifier = Modifier.semantics {
                            contentDescription = "Reload stocks"
                        },
                        onClick = {
                            viewModel.loadStocks()
                        }
                    ) {
                        Text(stringResource(R.string.refresh))
                    }
                }
            )
        }) { innerPadding ->

        StocksScreen(
            modifier = Modifier.padding(innerPadding),
            stocksViewState,
            onReloadClicked = {
                viewModel.loadStocks()
            }
        )
    }
}

// show different screen based on the stocksViewState
@Composable
fun StocksScreen(
    modifier: Modifier = Modifier,
    stocksViewState: StocksViewState,
    onReloadClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (stocksViewState) {

            is StocksViewState.UnInitialized -> EmptyStocksScreen(
                message = stringResource(R.string.load_stocks_message),
                buttonTitle = stringResource(R.string.load),
                onReloadClicked = onReloadClicked
            )

            is StocksViewState.Loading -> ProgressScreen()

            is StocksViewState.LoadingError -> EmptyStocksScreen(
                message = stringResource(stocksViewState.errorMessageId),
                buttonTitle = stringResource(R.string.try_again),
                onReloadClicked = onReloadClicked
            )

            is StocksViewState.Result -> StocksResultScreen(
                stocksViewState.result,
                onReloadClicked = onReloadClicked
            )
        }
    }
}

// screen showing loading screen with a circular progress indicator and a text
@Composable
fun ProgressScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator()
        Text("Loading")
    }
}

// screen showing the result of the stocks loading network request.
// it can either show a screen with stocks or an empty screen if there are none
@Composable
fun StocksResultScreen(
    stocks: Stocks,
    onReloadClicked: () -> Unit
) {

    if (stocks.stockList.isEmpty()) {
        EmptyStocksScreen(
            message = stringResource(R.string.no_stocks),
            buttonTitle = stringResource(R.string.reload),
            onReloadClicked = onReloadClicked
        )

    } else {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {

            items(stocks.stockList) { stock ->

                Text("Ticker: ${stock.ticker}")
                Text("Name: ${stock.name}")
                Text("Currency: ${stock.currency}")
                Text("Current Price: $${stock.priceInDollars()}")
                if (stock.quantity != null) {
                    Text("Quantity: ${stock.quantity}")
                }
                Text("Current Time: ${stock.currentDateTime()}")

                HorizontalDivider()
            }
        }
    }
}

// screen showing no stocks with given message and button with given buttonTitle.
// It is used -
// 1. when the stocks are not fetched yet i.e. Uninitialized view state
// 2. when the network query returns empty stock i.e. Result view state with empty stock list
// 3. when the network query returns errored result i.e. LoadingError view state
@Composable
fun EmptyStocksScreen(
    message: String,
    buttonTitle: String,
    onReloadClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = message
        )
        Button(
            onClick = onReloadClicked
        ) {
            Text(text = buttonTitle)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StocksListScreenPreviewLoading() {
    CashAppStocksTheme {
        StocksScreen(
            stocksViewState = StocksViewState.Loading,
            onReloadClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StocksListScreenPreviewError() {
    CashAppStocksTheme {
        StocksScreen(
            stocksViewState = StocksViewState.LoadingError(R.string.error_loading),
            onReloadClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StocksListScreenPreviewEmptyResult() {
    CashAppStocksTheme {
        StocksScreen(
            stocksViewState = StocksViewState.Result(Stocks(emptyList())),
            onReloadClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StocksListScreenPreviewResult() {
    CashAppStocksTheme {
        StocksScreen(
            stocksViewState = StocksViewState.Result(testData),
            onReloadClicked = {})
    }
}
