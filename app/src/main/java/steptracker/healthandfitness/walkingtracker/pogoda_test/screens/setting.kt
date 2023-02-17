package steptracker.healthandfitness.walkingtracker.pogoda_test.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import steptracker.healthandfitness.walkingtracker.pogoda_test.data.Cities
import steptracker.healthandfitness.walkingtracker.pogoda_test.data.MainViewModel
import steptracker.healthandfitness.walkingtracker.pogoda_test.ui.theme.Pogoda_testTheme
import kotlin.math.roundToInt

@Composable
fun setting(
    navController: NavController, viewModel: MainViewModel
) {
    Pogoda_testTheme {

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
        val scaffoldState = rememberScaffoldState()

        val city: String by viewModel.city.observeAsState("")
        val allCities by viewModel.allCities.observeAsState(listOf())

        val textColor = remember { mutableStateOf(Color.Unspecified) }

        Scaffold(scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    content = {Icon(Icons.Filled.Add, contentDescription = "Добавить")},
                    onClick = {
                        viewModel.onCityChange("")
                        viewModel.onTypeChange("")
                        viewModel.addCity = true
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Add City, type and  monthly Average Temperature ")
                        }
                    }
                )
            }
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
                ) {
                IconButton(onClick = { navController.navigate("weather") }) {
                    Icon(Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                SnackbarHost(snackbarHostState.value)
                
                AnimatedVisibility(visible = !viewModel.addCity) {
                    ListCityEdit(listCities = allCities, edit = "edit", delete = "delete",viewModel)
                }

                AnimatedVisibility(visible = viewModel.addCity) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)) {

                        InputCity( seasons = listOf("winter","spring","samm","autum"),
                            city = city, onCityChange = {viewModel.onCityChange(it)},
                         viewModel = viewModel, manths = listOf("Desem","Januar","Februar","March","April","May","June","July","August","Septem","Octob","Novem") )

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), horizontalArrangement = Arrangement.End) {
                            Button(onClick = {
                                if (viewModel.city.value != "" && viewModel.type.value != "" ) {
                                    if (allCities.all { it.cityName != viewModel.city.value } || allCities.size == 0){

                                        viewModel.type.value?.let { it1 ->
                                            viewModel.city.value?.let { it2 ->
                                                Cities(
                                                    it2,
                                                    it1,viewModel.averageWinter(),viewModel.averageSpring(),viewModel.averageSummer(),viewModel.averageAutumn())
                                            }
                                        }?.let { it2 -> viewModel.insertCity(it2) }

                                        viewModel.addCity = false
                                    }
                                    else {
                                        viewModel.city.value?.let { it1 -> viewModel.type.value?.let { it2 ->
                                            viewModel.updateCity(it1,
                                                it2, viewModel.averageWinter(),viewModel.averageSpring(),viewModel.averageSummer(),viewModel.averageAutumn())
                                        } }
                                        viewModel.addCity = false
                                    }
                                }
                                else {
                                    scope.launch {
                                        snackbarHostState.value.showSnackbar("Add City, type and  monthly AverageTemperature")
                                    }
                                }
                            }) {
                                Text(text = "InputNewCity")
                            }
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    val checkedFarenCel = remember { mutableStateOf("С") }
                        Row (verticalAlignment = Alignment.CenterVertically ){
                            Switch(
                                checked = viewModel.isFahrenheit,
                                onCheckedChange = {
                                    viewModel.switchChange()
                                    if (!viewModel.isFahrenheit && !viewModel.isCelsium) {
                                        checkedFarenCel.value = "C"
                                        for (n in 0..viewModel.allCities.value!!.size - 1){
                                            viewModel.updateCity(allCities[n].cityName,
                                            allCities[n].type,((allCities[n].winter.toDouble() - 32) / 1.8).roundToInt().toString(),
                                                ((allCities[n].spring.toDouble() - 32) / 1.8).roundToInt().toString(),
                                                ((allCities[n].sammer.toDouble() - 32) / 1.8).roundToInt().toString(),
                                                ((allCities[n].autumn.toDouble() - 32) / 1.8).roundToInt().toString())
                                        }
                                    }else {
                                        checkedFarenCel.value = "F"
                                        for (n in 0..viewModel.allCities.value!!.size - 1){
                                            viewModel.updateCity(allCities[n].cityName,
                                                allCities[n].type,((allCities[n].winter.toDouble() * 1.8) + 32).roundToInt().toString(),
                                                ((allCities[n].spring.toDouble() * 1.8) + 32).roundToInt().toString(),
                                                ((allCities[n].sammer.toDouble() * 1.8) + 32).roundToInt().toString(),
                                                ((allCities[n].autumn.toDouble() * 1.8) + 32).roundToInt().toString())
                                        }
                                    }
                                }
                            )
                            Text(checkedFarenCel.value, fontSize = 35.sp, color = textColor.value, modifier = Modifier.width(100.dp))
                        }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputCity(city: String, onCityChange: (String) -> Unit,
              seasons: List<String>,
              manths: List<String>,
              viewModel: MainViewModel
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)
        .background(Color.LightGray)) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                TextField(
                    value = city,
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(4.dp),
                    onValueChange = onCityChange,
                    label = { Text("Input City") },
                    singleLine = true
                )
                var expanded by remember { mutableStateOf(false) }

                Box() {
                    Text(text = "InputType ->", modifier = Modifier
                        .clickable(onClick = { expanded = true })
                        .padding(20.dp))
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(onClick = { viewModel.onTypeChange("small")
                            expanded = false
                        }) {
                            Text(text = "small", modifier = Modifier.padding(5.dp))
                        }
                        Divider(color = Color.Blue, modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp))
                        DropdownMenuItem(onClick = { viewModel.onTypeChange( "medium")
                            expanded = false
                        }) {
                            Text(text = "medium", modifier = Modifier.padding(5.dp))
                        }
                        Divider(color = Color.Blue, modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp))
                        DropdownMenuItem(onClick = { viewModel.onTypeChange("large")
                            expanded = false
                        }) {
                            Text(text = "large", modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Box(modifier = Modifier
                    .padding(top = 4.dp)
                    .weight(0.25f)) {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(1),
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(0.dp)
                    ) {

                        items(seasons.size) { index ->
                            Card(backgroundColor = Color.Blue,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxSize()) {
                                Text(
                                    "${seasons[index]}",
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
                Box(modifier = Modifier
                    .weight(0.75f)
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(0.dp)
                    ) {

                        items(12) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()) {

                                val textState = remember { mutableStateOf("") }
                                TextField(
                                    value = textState.value,
                                    onValueChange = {
                                        viewModel.convertTemp(it,index )
                                        textState.value = it
                                    },
                                    modifier = Modifier.padding(2.dp),
                                    label = { Text("${manths[index]}") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListCityEdit(listCities: List<Cities>, edit: String, delete: String, viewModel: MainViewModel) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(
                10.dp
            )) {
        items(listCities.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(listCities[index].cityName, modifier = Modifier
                    .weight(0.4f))
                Text(edit, modifier = Modifier
                    .weight(0.2f)
                    .clickable {
                        viewModel.onCityChange(listCities[index].cityName)
                        viewModel.onTypeChange(listCities[index].type)
                        viewModel.addCity = true
                    })
                Text(delete, modifier = Modifier
                    .weight(0.2f)
                    .clickable {
                        viewModel.deleteCity(listCities[index].cityName)
                    })
            }
        }
    }
}