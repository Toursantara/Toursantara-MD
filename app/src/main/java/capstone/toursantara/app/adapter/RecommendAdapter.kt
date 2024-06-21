package capstone.toursantara.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.databinding.ItemPlaceBinding
import capstone.toursantara.app.model.Data
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.view.detail.DetailActivity
import java.text.NumberFormat
import java.util.Locale

class RecommendAdapter(private val recommendations: MutableList<Place>) : RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    class RecommendViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val place = recommendations[position]
        with(holder) {
            with(recommendations[position]) {
                binding.placeName.text = place_name
                binding.placeCategory.text = category
                binding.placeCity.text = city
                binding.placePrice.text = price

                try {
                    val priceNumber = place.price?.toDouble()
                    binding.placePrice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).run {
                        maximumFractionDigits = 0
                        format(priceNumber)
                    }
                } catch (e: NumberFormatException) {
                    binding.placePrice.text = "Rp -"
                }
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("place", place)
                itemView.context.startActivity(intent)
            }

        }

    }

    override fun getItemCount(): Int = recommendations.size
}