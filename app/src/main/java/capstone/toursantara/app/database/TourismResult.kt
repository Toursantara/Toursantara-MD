package capstone.toursantara.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tourism_result")
data class TourismResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val placeName: String,
    val city: String,
    val category: String,
    val price: Double?,
    val rating: String?,
    val description: String,
    val lat: Double,
    val long: Double,
    var timestamp: Long
)
