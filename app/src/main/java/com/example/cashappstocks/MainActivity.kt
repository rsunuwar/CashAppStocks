package com.example.cashappstocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cashappstocks.ui.CashAppStocksNavigation
import com.example.cashappstocks.ui.theme.CashAppStocksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashAppStocksTheme {
                CashAppStocksNavigation()
            }
        }
    }
}
