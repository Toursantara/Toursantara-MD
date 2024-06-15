package capstone.toursantara.app.model

data class Place(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Double,
    val rating: Double,
    val timeMinutes: Int,
    val lat: Double,
    val lng: Double
)

data class Rating(
    val userId: String,
    val placeId: String,
    val placeRating: Double
)