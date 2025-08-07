package com.example.cashappstocks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.cashappstocks.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClicked: () -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.about),
                    fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Click back",
                    modifier = Modifier.clickable {
                        onBackClicked()
                    }
                )
            }
        )
    }) { paddingValues ->

        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.about_page),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
