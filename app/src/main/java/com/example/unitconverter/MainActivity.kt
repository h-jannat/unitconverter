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
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.pow

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
        var inputValue: Float? by remember {
            mutableStateOf(null)
        }
        var result: Float? by remember {
            mutableStateOf(null)
        }
        val unitsMap: Map<String, Float> = mapOf(
            "mm" to 10F.pow(3), "cm" to 10F.pow(2),
            "m" to 1F, "Km" to 10F.pow(-3)
        )
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
            value = if (inputValue == null) "" else inputValue.toString(),
            onValueChange
            = {
                inputValue = it.toFloat()

            })
        Row {
            val context = LocalContext.current
            var isFromDropdownExpanded: Boolean by remember {
                mutableStateOf(false)
            }
            var fromUnit: String? by remember {
                mutableStateOf(null)
            }
            var isToDropdownExpanded by remember {
                mutableStateOf(false)
            }
            var toUnit: String? by remember {
                mutableStateOf(null)
            }

            fun convert() {
                if (fromUnit != null && unitsMap[toUnit] != null && inputValue !=
                    null
                ) {
                    val factor =
                        unitsMap[toUnit]?.div(unitsMap[fromUnit]!!) as Float
                    result = inputValue!! * factor
                }
            }
            DropDown(
                buttonText = "From",
                isFromDropdownExpanded,
                fun() {
                    isFromDropdownExpanded = !isFromDropdownExpanded
                },
                fromUnit,
                fun(unit: String) {
                    fromUnit = unit
                    convert()
                }
            )
            Spacer(modifier = Modifier.width(Dp(10f)))
            DropDown(
                buttonText = "To",
                isToDropdownExpanded,
                fun() {
                    isToDropdownExpanded = !isToDropdownExpanded
                },
                toUnit,
                fun(unit: String) {
                    toUnit = unit
                    convert()
                }
            )
        }
        Text(text = "Result = ${if (result != null) result else ""} ")

    }
}

@Composable
fun DropDown(
    buttonText: String, isExpanded: Boolean,
    setIsExpanded: () -> (Unit) = {},
    selectedUnit: String?,
    onSelectUnit: (String) -> (Unit) = {},
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
            listOf("mm", "cm", "m", "Km").map {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { onSelectUnit(it) },
                    colors = if (it == selectedUnit)
                        MenuDefaults.itemColors(
                            textColor =
                            Color
                                .Blue
                        ) else MenuDefaults.itemColors()
                )

            }
        }
    }
}

