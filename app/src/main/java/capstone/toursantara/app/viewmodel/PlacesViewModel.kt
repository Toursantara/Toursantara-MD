package capstone.toursantara.app.viewmodel

import android.util.Log
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
                liveData.postValue(places)
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }
}
