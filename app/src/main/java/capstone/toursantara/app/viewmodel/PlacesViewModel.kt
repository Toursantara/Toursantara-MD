package capstone.toursantara.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.model.Rating
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PlacesViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference()

    fun fetchAllPlaces(): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()
        dbRef.child("places").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val places = mutableListOf<Place>()
                dataSnapshot.children.mapNotNullTo(places) { it.getValue(Place::class.java) }
                liveData.value = places
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
        return liveData
    }

    fun getRecommendations(userId: String): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()
        dbRef.child("ratings").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ratings = mutableListOf<Rating>()
                dataSnapshot.children.mapNotNullTo(ratings) { it.getValue(Rating::class.java) }
                // Assume predictRecommendations is a function that returns a list of recommended places
                val recommendedPlaces = predictRecommendations(ratings)
                liveData.value = recommendedPlaces
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
        return liveData
    }

    private fun predictRecommendations(ratings: List<Rating>): List<Place> {
        // Dummy implementation
        return listOf()
    }
}