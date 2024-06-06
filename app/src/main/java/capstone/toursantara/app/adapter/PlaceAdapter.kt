package capstone.toursantara.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.network.model.Places
import com.bumptech.glide.Glide

class PlaceAdapter(private val placeList: List<Places.PlacesItem>) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.placeName
        holder.placePrice.text = "Rp.${place.price}"
        holder.placeLocation.text = place.city
        holder.placeRating.text = place.rating.toString()

        // Load image using Glide or any other image loading library
        Glide.with(holder.itemView.context)
            .load("https://via.placeholder.com/150") // Replace with actual image URL if available
            .into(holder.placeImage)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeImage: ImageView = itemView.findViewById(R.id.place_image)
        val placeName: TextView = itemView.findViewById(R.id.place_name)
        val placePrice: TextView = itemView.findViewById(R.id.place_price)
        val placeLocation: TextView = itemView.findViewById(R.id.place_location)
        val placeRating: TextView = itemView.findViewById(R.id.place_rating)

    }
}