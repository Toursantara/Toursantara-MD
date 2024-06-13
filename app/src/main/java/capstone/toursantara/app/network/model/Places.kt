package capstone.toursantara.app.network.model


import com.google.gson.annotations.SerializedName

class Places : ArrayList<Places.PlacesItem>(){
    data class PlacesItem(
        @SerializedName("category")
        val category: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("long")
        val long: Double,
        @SerializedName("place_id")
        val placeId: Int,
        @SerializedName("place_name")
        val placeName: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("time_minutes")
        val timeMinutes: Int
    )
}