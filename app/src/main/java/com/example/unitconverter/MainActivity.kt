package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverter() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "UnitConverter", modifier = Modifier.padding(
                bottom = Dp
                    (16f)
            )
        )
        OutlinedTextField(
            modifier = Modifier.padding(
                bottom = Dp
                    (16f)
            ),
            value = "", onValueChange = {

            })
        Row {
            val context = LocalContext.current
            var isFromDropdownExpanded by remember {
                mutableStateOf(false)
            }

            fun setIsFromDropdownExpanded() {
                println("setIsFromDropdownExpanded : $isFromDropdownExpanded")
                isFromDropdownExpanded = !isFromDropdownExpanded
            }


            var isToDropdownExpanded by remember {
                mutableStateOf(false)
            }

            fun setIsToDropdownExpanded() {
                println("setIsFromDropdownExpanded clicked")
                isToDropdownExpanded = !isToDropdownExpanded
            }
            DropDown(
                buttonText = "From",
                isFromDropdownExpanded,
                ::setIsFromDropdownExpanded
            )


            Spacer(modifier = Modifier.width(Dp(10f)))
            DropDown(
                buttonText = "To",
                isToDropdownExpanded,
                ::setIsToDropdownExpanded
            )
        }
        Text(text = "Result = ")

    }
}

@Composable
fun DropDown(
    buttonText: String, isExpanded: Boolean,
    setIsExpanded: () -> (Unit) = {}

) {

    Box() {
        Button(
            onClick = setIsExpanded

        ) {
            Text(text = buttonText)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "arrow down"
            )
        }
        DropdownMenu(expanded = isExpanded,
            onDismissRequest
            = {
                setIsExpanded()
            }) {
            listOf("cm", "m", "C", "F").map {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { /*TODO*/ })
            }
        }
    }
}

