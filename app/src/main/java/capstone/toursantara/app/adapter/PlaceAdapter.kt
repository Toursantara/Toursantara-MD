package capstone.toursantara.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.view.bookmarks.BookmarksViewModel
import capstone.toursantara.app.view.detail.DetailActivity
import java.text.NumberFormat
import java.util.*

class PlaceAdapter(private var places: List<Place> = listOf()) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName: TextView = itemView.findViewById(R.id.place_name)
        val placeCategory: TextView = itemView.findViewById(R.id.place_category)
        val placeCity: TextView = itemView.findViewById(R.id.place_city)
        val placePrice: TextView = itemView.findViewById(R.id.place_price)
        val placeRating: TextView = itemView.findViewById(R.id.place_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        with(holder) {
            placeName.text = place.place_name
            placeCategory.text = place.category
            placeCity.text = place.city

            // Pengecekan dan pemformatan untuk price
            val priceNumber = place.price?.toDoubleOrNull()
            placePrice.text = if (priceNumber != null) {
                NumberFormat.getCurrencyInstance(Locale("in", "ID")).run {
                    maximumFractionDigits = 0
                    format(priceNumber)
                }
            } else {
                "Rp -"
            }

            // Pengecekan dan pemformatan untuk rating
            val rating = place.rating?.toDoubleOrNull() ?: 0.0
            placeRating.text = String.format("%.1f", rating)

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("place", place)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun updatePlaces(newPlaces: List<Place>) {
        val diffCallback = PlaceDiffCallback(places, newPlaces)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        places = newPlaces
        diffResult.dispatchUpdatesTo(this)
    }

    fun observeBookmarks(lifecycleOwner: LifecycleOwner, viewModel: BookmarksViewModel) {
        viewModel.bookmarks.observe(lifecycleOwner) { bookmarks ->
            bookmarks?.let {
                val places = bookmarks.map { tourismResult ->
                    Place(
                        place_name = tourismResult.placeName,
                        category = tourismResult.category,
                        city = tourismResult.city,
                        price = tourismResult.price?.toString() ?: "Rp -",
                        rating = tourismResult.rating ?: "0.0",
                        description = tourismResult.description,
                        lat = tourismResult.lat.toString(),
                        long = tourismResult.long.toString()
                    )
                }
                updatePlaces(places)
            }
        }
    }

    class PlaceDiffCallback(
        private val oldList: List<Place>,
        private val newList: List<Place>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].place_id == newList[newItemPosition].place_id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
