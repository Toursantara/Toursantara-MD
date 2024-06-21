package capstone.toursantara.app.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.RecommendAdapter
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.network.ApiClientRecommend
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RecommendFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecommendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recommend, container, false)

        adapter = RecommendAdapter(mutableListOf())
        recyclerView = rootView.findViewById(R.id.rv_recommend)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fetchRecommendations(7)

        return rootView
    }

    private fun fetchRecommendations(userId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            ApiClientRecommend.fetchRecommendations(userId, onSuccess = { placeIds ->
                fetchPlacesFromFirestore(placeIds)
            }, onError = { error ->
                error.printStackTrace()
            })
        }
    }

    private fun fetchPlacesFromFirestore(placeIds: List<Int>) {
        val firestore = FirebaseFirestore.getInstance()
        val places = mutableListOf<Place>()

        placeIds.forEach { id ->
            firestore.collection("places").document(id.toString()).get().addOnSuccessListener { document ->
                document.toObject(Place::class.java)?.let { place ->
                    places.add(place)
                    if (places.size == placeIds.size) {
                        adapter = RecommendAdapter(places)
                        recyclerView.adapter = adapter
                    }
                }
            }.addOnFailureListener {
                // Handle failure
            }
        }
    }
}
