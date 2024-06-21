package capstone.toursantara.app.network

import capstone.toursantara.app.model.Data
import capstone.toursantara.app.model.RecommendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientRecommend {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://toursantara-recomendation-gnqcrqbmxa-et.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun fetchRecommendations(userId: Int, onSuccess: (List<Int>) -> Unit, onError: (Throwable) -> Unit) {
        apiService.getRecommendations(mapOf("user_id" to userId)).enqueue(object :
            Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                if (response.isSuccessful) {
                    val placeIds = response.body()?.data?.map { it.id }
                    if (placeIds != null) {
                        onSuccess(placeIds)
                    } else {
                        onError(Exception("No data found"))
                    }
                } else {
                    onError(Exception("Response not successful: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                onError(t)
            }
        })
    }
}