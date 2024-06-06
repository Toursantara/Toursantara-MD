package capstone.toursantara.app.network.api

import capstone.toursantara.app.network.model.Places
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("getplaces")
    fun getPlaces(): Call<ArrayList<Places.PlacesItem>>

}