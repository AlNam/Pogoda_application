package steptracker.healthandfitness.walkingtracker.pogoda_test.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.math.roundToInt

class CitiesRepozitory(private val citiesDao: CitiesDao) {

    val allCities: LiveData <List<Cities>> = citiesDao.getAllCity()
    val searchCities = MutableLiveData<List<Cities>>()
    private val corotineScope = CoroutineScope(Dispatchers.Main)

    fun insertCity(city: Cities) {
        corotineScope.launch(Dispatchers.IO) {
            citiesDao.insertCity(city)
        }
    }

    fun citiesFind(name: String) {
        corotineScope.launch(Dispatchers.Main) {
            searchCities.value = asyncFind(name).await()
        }
    }
    private fun asyncFind(name: String): Deferred<List<Cities>> =
        corotineScope.async(Dispatchers.IO) {
            return@async citiesDao.findCity(name)
        }

    fun deleteCity(name: String) {
        corotineScope.launch(Dispatchers.IO) {
            citiesDao.deleteCity(name)
        }
    }
    fun updateCity(
        name: String,
        type: String,
        winter: String,
        spring: String,
        sammer: String,
        autumn: String
    ) {
        corotineScope.launch(Dispatchers.IO) {
            citiesDao.updateCity(
                name,type,winter,spring,sammer,autumn
            )
        }
    }
}