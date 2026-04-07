package com.ghaldanurzahrah0016.assessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ghaldanurzahrah0016.assessment1.R
import com.ghaldanurzahrah0016.assessment1.navigation.Screen
import com.ghaldanurzahrah0016.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerpadding ->
        ScreenContent(Modifier.padding(innerpadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var nilai by rememberSaveable { mutableStateOf("") }
    var nilaierror by rememberSaveable { mutableStateOf(false) }

    val radioOption = listOf(
        "inch" to "cm",
        "feet" to "cm",
        "pound" to "kg",
        "gallon" to "liter"
    )

    var satuan by rememberSaveable { mutableStateOf(radioOption[0]) }
    val hasilSatuan = satuan.second

    var hasil by rememberSaveable { mutableDoubleStateOf(0.0) }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = stringResource(R.string.masukan_nilai))
        OutlinedTextField(
            value = nilai,
            onValueChange = { nilai = it },
            label = { Text(text = stringResource(R.string.nilai)) },
            trailingIcon = {IconPicker(nilaierror, hasilSatuan)},
            supportingText = {ErrorHint(nilaierror)},
            isError = nilaierror,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = stringResource(R.string.pilih_satuan))
        Column(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .selectableGroup()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            radioOption.forEach { option ->
                SatuanOption(
                    label = option.first,
                    isSelected = satuan == option,
                    modifier = Modifier
                        .selectable(
                            selected = satuan == option,
                            onClick = { satuan = option },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
        Button(
            onClick = {
                nilaierror = (nilai == "")
                if (nilaierror) return@Button

                val input = nilai.toDoubleOrNull() ?: 0.0

                hasil = when (satuan.first) {
                    "inch" -> convertInch(input)
                    "feet" -> convertFeet(input)
                    "pound" -> convertPound(input)
                    "gallon" -> convertGallon(input)
                    else -> 0.0
                }
            },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        //

        Text(
            text = stringResource(R.string.hasil, hasil, satuan.first),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SatuanOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

fun convertInch(nilai: Double): Double {
    return nilai/ 2.54
}

fun convertFeet(nilai: Double): Double {
    return nilai / 30.48
}

fun convertPound(nilai: Double): Double {
    return nilai * 2.205
}

fun convertGallon(nilai: Double): Double {
    return nilai / 3.785
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}