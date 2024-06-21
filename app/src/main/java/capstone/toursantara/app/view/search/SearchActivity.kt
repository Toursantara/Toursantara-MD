package capstone.toursantara.app.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.PlaceAdapter
import capstone.toursantara.app.model.Place
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchResults: RecyclerView
    private lateinit var adapter: PlaceAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchInput = findViewById(R.id.search_input)
        searchResults = findViewById(R.id.search_results)
        searchResults.layoutManager = LinearLayoutManager(this)
        adapter = PlaceAdapter()
        searchResults.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchPlaces(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchPlaces(query: String) {
        db.collection("places")
            .get()
            .addOnSuccessListener { result ->
                val placesList = result.toObjects(Place::class.java)
                val filteredList = placesList.filter { it.place_name?.contains(query, true) == true }
                adapter.updatePlaces(filteredList)
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }
}