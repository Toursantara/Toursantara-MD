// HomeFragment.kt
package capstone.toursantara.app.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.CarouselPriceRangeAdapter
import capstone.toursantara.app.adapter.CategoryAdapter
import capstone.toursantara.app.adapter.PlaceAdapter
import capstone.toursantara.app.model.Category
import capstone.toursantara.app.viewmodel.PlacesViewModel
import capstone.toursantara.app.viewmodel.PlacesViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var rvPlaces: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    private val placesViewModel: PlacesViewModel by viewModels { PlacesViewModelFactory() }

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
        placeAdapter = PlaceAdapter(emptyList())
        rvPlaces.adapter = placeAdapter

        // Observe data from ViewModel
        placesViewModel.fetchAllPlaces().observe(viewLifecycleOwner, Observer { places ->
            placeAdapter.updatePlaces(places)
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