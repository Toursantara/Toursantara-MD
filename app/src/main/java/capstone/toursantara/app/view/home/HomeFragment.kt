// HomeFragment.kt
package capstone.toursantara.app.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.CategoryAdapter
import capstone.toursantara.app.adapter.PlaceAdapter
import capstone.toursantara.app.model.Category
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.view.search.SearchActivity
import capstone.toursantara.app.viewmodel.PlacesViewModel
import capstone.toursantara.app.viewmodel.PlacesViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects

class HomeFragment : Fragment(), CategoryAdapter.CategoryClickListener {

    private lateinit var rvPlaces: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    private val placesViewModel: PlacesViewModel by viewModels { PlacesViewModelFactory() }
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPlaces = view.findViewById(R.id.rv_places)
        rvPlaces.layoutManager = LinearLayoutManager(context)
        placeAdapter = PlaceAdapter(emptyList())
        rvPlaces.adapter = placeAdapter

        val rvCategory: RecyclerView = view.findViewById(R.id.rv_category)
        val categoryLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategory.layoutManager = categoryLayoutManager
        val categoryAdapter = CategoryAdapter(getCategoryList(), this)
        rvCategory.adapter = categoryAdapter

        placesViewModel.fetchAllPlaces().observe(viewLifecycleOwner, Observer { places ->
            placeAdapter.updatePlaces(places)
        })

        fetchPlacesFromFirestore()

        val searchButton = view.findViewById<FloatingActionButton>(R.id.search_fab_btn)
        searchButton.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCategoryList(): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(Category(R.drawable.ondellll, "Budaya"))
        categoryList.add(Category(R.drawable.tmnhiburan, "Taman Hiburan"))
        categoryList.add(Category(R.drawable.treeee, "Cagar Alam"))
        categoryList.add(Category(R.drawable.beach, "Bahari"))
        categoryList.add(Category(R.drawable.mosque, "Tempat Ibadah"))
        categoryList.add(Category(R.drawable.mall, "Pusat Perbelanjaan"))
        return categoryList
    }

    private fun fetchPlacesFromFirestore() {
        firestore.collection("places")
            .get()
            .addOnSuccessListener { result ->
                val places = result.toObjects<Place>()
                placeAdapter.updatePlaces(places)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    override fun onCategoryClick(categoryName: String) {
        fetchPlacesByCategory(categoryName)
    }

    private fun fetchPlacesByCategory(category: String) {
        firestore.collection("places")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { result ->
                val places = result.toObjects<Place>()
                placeAdapter.updatePlaces(places)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }
}
