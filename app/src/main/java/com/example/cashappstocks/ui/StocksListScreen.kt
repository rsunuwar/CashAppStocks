package com.example.cashappstocks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashappstocks.R
import com.example.cashappstocks.models.Stocks
import com.example.cashappstocks.ui.theme.CashAppStocksTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StocksListScreen(viewModel: StocksListViewModel) {

    val stocksList by viewModel.stockListFlow.collectAsState()

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
            stocksList,
            onReloadClicked = {
                viewModel.loadStocks()
            }
        )
    }
}

@Composable
fun StocksScreen(
    modifier: Modifier = Modifier,
    stocks: Stocks,
    onReloadClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (stocks.stockList.isEmpty()) {

            EmptyStocksScreen(onReloadClicked = onReloadClicked)

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
}

@Composable
fun EmptyStocksScreen(
    onReloadClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(R.string.no_stocks)
        )
        Button(
            onClick = onReloadClicked
        ) {
            Text(text = stringResource(R.string.reload))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StocksListScreenPreview() {
    CashAppStocksTheme {
        StocksScreen(stocks = Stocks(stockList = emptyList()), onReloadClicked = {})
//        StocksScreen(stocks = testData, onReloadClicked = {})
    }
}
