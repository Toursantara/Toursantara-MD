package capstone.toursantara.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.CarouselPriceRangeAdapter
import capstone.toursantara.app.adapter.Category
import capstone.toursantara.app.adapter.CategoryAdapter
import capstone.toursantara.app.adapter.PlaceAdapter
import capstone.toursantara.app.network.api.ApiClient
import capstone.toursantara.app.network.api.ApiClient.apiService
import capstone.toursantara.app.network.api.ApiService
import capstone.toursantara.app.network.model.Places
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var rvPlaces: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the price range RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_price_range)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val priceRanges = listOf(
            "> Rp.50.000",
            "Rp.50.000 - Rp.100.000",
            "> Rp.500.000",
            "> Rp.50.000"
        )

        val adapter = CarouselPriceRangeAdapter(priceRanges)
        recyclerView.adapter = adapter

        // Set up the carousel
        val carousel: Carousel = view.findViewById(R.id.carousel)
        carousel.setAdapter(adapter)

        // RV CATEGORY
        val rvCategory: RecyclerView = view.findViewById(R.id.rv_category)
        val categoryLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategory.layoutManager = categoryLayoutManager
        rvCategory.adapter = CategoryAdapter(getCategoryList())

        // Set up the places RecyclerView
        rvPlaces = view.findViewById(R.id.rv_places)
        rvPlaces.layoutManager = LinearLayoutManager(context)

        fetchPlaces()
    }

    private fun fetchPlaces() {
        apiService.getPlaces().enqueue(object : Callback<ArrayList<Places.PlacesItem>> {
            override fun onResponse(call: Call<ArrayList<Places.PlacesItem>>, response: Response<ArrayList<Places.PlacesItem>>) {
                if (response.isSuccessful) {
                    val places = response.body() ?: emptyList()
                    rvPlaces.adapter = PlaceAdapter(places)
                    Log.d("HomeFragment", "Places loaded: ${places.size}")
                } else {
                    Toast.makeText(context, "Failed to load places", Toast.LENGTH_SHORT).show()
                    Log.e("HomeFragment", "Failed to load places: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<Places.PlacesItem>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("HomeFragment", "Error: ${t.message}", t)
            }
        })
    }

    private fun getCategoryList(): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(Category(R.drawable.ic_placeholder, "Category 1"))
        categoryList.add(Category(R.drawable.ic_placeholder, "Category 2"))
        categoryList.add(Category(R.drawable.ic_placeholder, "Category 3"))
        categoryList.add(Category(R.drawable.ic_placeholder, "Category 4"))
        categoryList.add(Category(R.drawable.ic_placeholder, "Category 5"))
        // Add more categories as needed
        return categoryList
    }
}
