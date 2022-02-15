package com.spirit.cargo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.presentation.theme.CargoTheme

class MainActivity : ComponentActivity() {

    private val requestItems =
        listOf(
            CargoRequest(id = 1, title = "12", url = "21"),
            CargoRequest(id = 2, title = "1222", url = "24211")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CargoTheme { HomeScreen() }
        }
    }

    @Composable
    fun HomeScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cargo") },
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 12.dp,
                )
            }, content = {
                LazyColumn(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(requestItems) { item ->
                        CargoItem(item, 2)
                    }
                }
            })
    }

    @Composable
    fun CargoItem(cargoRequest: CargoRequest, orderCount: Int) {
        var isChecked by remember { mutableStateOf(false) }

        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable { isChecked = !isChecked }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 8.dp),
            ) {
                Column {
                    Text(text = cargoRequest.title)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Order count: $orderCount")
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = !isChecked },
                )
            }
        }
    }

    @Preview
    @Composable
    fun PreviewHomeScreen() {
        CargoTheme { HomeScreen() }
    }
}
