package steptracker.healthandfitness.walkingtracker.pogoda_test.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
class Cities {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "city_id")
    var id: Int = 0

    @ColumnInfo(name = "city_name") var cityName: String = ""
    @ColumnInfo(name = "type") var type: String = ""
    @ColumnInfo(name = "winter") var winter: String = "0"
    @ColumnInfo(name = "spring") var spring: String = "0"
    @ColumnInfo(name = "sammer") var sammer: String = "0"
    @ColumnInfo(name = "autumn") var autumn: String = "0"

    constructor() {}
    constructor(
        city_name: String,
        type: String,
        winter: String,
        spring: String,
        sammer: String,
        autumn: String
    ) {
        this.id = id
        this.cityName = city_name
        this.type = type
        this.winter = winter
        this.spring = spring
        this.sammer = sammer
        this.autumn = autumn
    }

}