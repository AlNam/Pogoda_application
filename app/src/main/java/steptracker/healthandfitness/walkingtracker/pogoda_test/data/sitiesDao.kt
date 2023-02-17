package steptracker.healthandfitness.walkingtracker.pogoda_test.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(cities: Cities)

    @Query("SELECT * FROM city WHERE city_name = :name")
    fun findCity(name: String): List<Cities>

    @Query("DELETE  FROM city WHERE city_name = :name")
    fun deleteCity(name: String)

    @Query("SELECT * FROM city")
    fun getAllCity(): LiveData <List<Cities>>

    @Query("UPDATE city SET city_name = :name," +
            "type = :type," +
            "winter = :winter," +
            "spring = :spring," +
            "sammer = :sammer," +
            "autumn = :autumn " +
            "WHERE city_name = :name"
    )
    fun updateCity(name: String,
                   type: String,
                   winter: String,
                   spring: String,
                   sammer: String,
                   autumn: String)

    @Query("UPDATE city SET winter = :winter," +
            "spring = :spring," +
            "sammer = :sammer," +
            "autumn = :autumn "
    )
    fun celsiumFarenChange(winter: String,
                    spring: String,
                    sammer: String,
                    autumn: String)

}