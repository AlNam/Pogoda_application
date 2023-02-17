package steptracker.healthandfitness.walkingtracker.pogoda_test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cities::class], version = 1)
abstract class CitiesRoomDatabase: RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    companion object {
        private var INSTANCE: CitiesRoomDatabase? = null
        fun getInstance(context: Context): CitiesRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CitiesRoomDatabase::class.java,
                        "cities_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}