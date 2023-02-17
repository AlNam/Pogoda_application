package steptracker.healthandfitness.walkingtracker.pogoda_test.data

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.*
import kotlin.math.roundToInt
import kotlin.reflect.KProperty

class MainViewModel(application: Application): ViewModel() {

    var seasons by mutableStateOf("Summer")

    var addCity by  mutableStateOf(false)

    private val _city: MutableLiveData<String> = MutableLiveData("")
    val city: LiveData<String> = _city
    fun onCityChange(newCity: String) {
        _city.value = newCity
    }
    private val _type: MutableLiveData<String> = MutableLiveData("")
    val type: LiveData<String> = _type
    fun onTypeChange(newType: String) {
        _type.value = newType
    }
    fun isNumber(s: String): Boolean {
        return !s.isNullOrEmpty() && s.matches(Regex("-?\\d?\\d?[.]?\\d?"))
    }
    fun isNumberValid(s2: String): Boolean {

        if (!s2.isNullOrEmpty() && s2.matches(Regex("\\d?")) ||  s2.length > 1  ) {
            return true
        }else return false

    }

    var number1 = "0"
    var number2 = "0"
    var number3 = "0"
    var number4 = "0"
    var number5 = "0"
    var number6 = "0"
    var number7 = "0"
    var number8 = "0"
    var number9 = "0"
    var number10 = "0"
    var number11 = "0"
    var number12 = "0"

    val numbersTempManth = arrayListOf(number1, number2, number3, number4, number5, number6, number7, number8, number9, number10, number11, number12)

    fun averageWinter(): String {
        var temp = ((numbersTempManth[0].toDouble() + numbersTempManth[1].toDouble() + numbersTempManth[2].toDouble()) / 3).toString().substringBeforeLast('.')
        return temp
    }
    fun averageSpring(): String {
        var temp = ((numbersTempManth[3].toDouble() + numbersTempManth[4].toDouble() + numbersTempManth[5].toDouble()) / 3).toString().substringBeforeLast('.')
        return temp
    }
    fun averageSummer(): String {
        var temp = ((numbersTempManth[6].toDouble() + numbersTempManth[7].toDouble() + numbersTempManth[8].toDouble()) / 3).toString().substringBeforeLast('.')
        return temp
    }
    fun averageAutumn(): String {
        var temp = ((numbersTempManth[9].toDouble() + numbersTempManth[10].toDouble() + numbersTempManth[11].toDouble()) / 3).toString().substringBeforeLast('.')
        return temp
    }

    val allCities: LiveData <List<Cities>>
    private val repository: CitiesRepozitory
    val searchResults: MutableLiveData<List<Cities>>

    init {
        val citiesDb = CitiesRoomDatabase.getInstance(application)
        val citiesDao = citiesDb.citiesDao()
        repository = CitiesRepozitory(citiesDao)

        allCities = repository.allCities
        searchResults = repository.searchCities
    }
    fun insertCity(city: Cities) {
        repository.insertCity(city)
    }
    fun findCity(name: String) {
        repository.citiesFind(name)
    }
    fun deleteCity(name: String) {
        repository.deleteCity(name)
    }
    fun updateCity(
        name: String,
        type: String,
        winter: String,
        spring: String,
        sammer: String,
        autumn: String
    ) {
        repository.updateCity(name,type,winter,spring,sammer,autumn)
    }

    fun convertTemp(temp: String, indexTemp: Int) {
        try {
            var tempInt = temp
            if (isNumber(tempInt)) {
            }
            if (isNumberValid(tempInt)) tempInt.let { it1 ->
                numbersTempManth[indexTemp] = tempInt
            }
        }catch (e: Exception) {

        }
    }

    var isFahrenheit by mutableStateOf(false)
    var isCelsium by  mutableStateOf(false)
    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }

}


