package capstone.toursantara.app.network

import capstone.toursantara.app.model.RecommendResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("recommend/")
    fun getRecommendations(@Body userId: Map<String, Int>): Call<RecommendResponse>
}