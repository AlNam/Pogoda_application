package steptracker.healthandfitness.walkingtracker.pogoda_test.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import steptracker.healthandfitness.walkingtracker.pogoda_test.BuildConfig
import steptracker.healthandfitness.walkingtracker.pogoda_test.data.Cities
import steptracker.healthandfitness.walkingtracker.pogoda_test.data.MainViewModel
import steptracker.healthandfitness.walkingtracker.pogoda_test.ui.theme.Pogoda_testTheme

@Composable
fun weather(
    viewModel: MainViewModel,
    navController: NavController,
) {
    Pogoda_testTheme() {
        Scaffold() {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                val allCities by viewModel.allCities.observeAsState(listOf())


                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(MaterialTheme.colors.primary)) {
                    Text("choise season  : ",
                        color = Color.White,
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(10.dp))
                    IconButton(onClick = {
                        navController.navigate("settings")
                    }) {
                        Icon(Icons.Filled.Settings,
                            contentDescription = "настройки",
                            modifier = Modifier.weight(0.2f),tint = Color.White
                        )
                    }
                }
                SelectableSeason(viewModel)
                TitleCityShowRow(headCity = "city" ,
                    headSeason = "${viewModel.seasons}" ,
                    headType = "type", MaterialTheme.colors.primary)
                listCities(allCities,allCities, allCities, viewModel)
            }
        }
    }
}


@Composable
fun SelectableSeason(viewModel: MainViewModel) {
    val colors = listOf(Color(0xFFFFF5A5),Color(0xFFFFA400) , Color(0xFF3FABFD), Color.Green)
    val selectedOption = remember { mutableStateOf(colors[0]) }

    Row(modifier =Modifier.padding(10.dp)) {
        Box(
            Modifier
                .padding(5.dp)
                .size(80.dp)
                .background(color = selectedOption.value),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "${viewModel.seasons}")
        }
        colors.forEach { color ->
            val selected = selectedOption.value == color

            Box(
                Modifier
                    .padding(4.dp)
                    .size(50.dp)
                    .background(color = color)
                    .selectable(
                        selected = selected,
                        onClick = {
                            selectedOption.value = color
                            when (color) {
                                Color(0xFFFFF5A5) -> viewModel.seasons = "Summer"
                                Color.Green -> viewModel.seasons = "Spring"
                                Color(0xFF3FABFD) -> viewModel.seasons = "Winter"
                                Color(0xFFFFA400) -> viewModel.seasons = "Autumn"
                            }
                        }
                    )
                    .border(
                        width = if (selected) {
                            2.dp
                        } else {
                            0.dp
                        },
                        color = Color.Black
                    )
            )
        }
    }
}

@Composable
fun TitleCityShowRow( headCity: String, headSeason: String, headType: String, backgroundColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(backgroundColor)
    ) {
        Text(headCity,
            color = Color.White,
            modifier = Modifier.weight(0.4f))
        Text(headSeason,
            color = Color.White,
            modifier = Modifier.weight(0.2f))
        Text(headType,
            color = Color.White,
            modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun listCities(allCities: List<Cities>, season: List<Cities>, type: List<Cities>,viewModel: MainViewModel) {

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(
                10.dp
            )) {
        items(allCities.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(allCities[index].cityName, modifier = Modifier
                    .weight(0.4f))
                when(viewModel.seasons) {
                    "Summer" -> Text(season[index].sammer, modifier = Modifier.weight(0.2f))
                    "Spring" -> Text(season[index].spring, modifier = Modifier.weight(0.2f))
                    "Winter" -> Text(season[index].winter, modifier = Modifier.weight(0.2f))
                    "Autumn" -> Text(season[index].autumn, modifier = Modifier.weight(0.2f))
                }
                if (BuildConfig.DEBUG) {
                    Log.d(TAG,"season is ${viewModel.seasons}")
                }
                Text(type[index].type, modifier = Modifier.weight(0.2f))
            }
        }
    }
}