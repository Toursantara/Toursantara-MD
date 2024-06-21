package capstone.toursantara.app.model

data class Data(
    val category: String,
    val city: String,
    val city_category: String,
    val id: Int,
    val lat: Double,
    val long: Double,
    var name: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    val rating: Double
)