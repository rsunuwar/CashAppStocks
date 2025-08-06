package com.example.cashappstocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cashappstocks.ui.StocksListScreen
import com.example.cashappstocks.ui.StocksListViewModel
import com.example.cashappstocks.ui.theme.CashAppStocksTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashAppStocksTheme {
                val stocksVM: StocksListViewModel by viewModels()
                StocksListScreen(viewModel = stocksVM)
            }
        }
    }
}
