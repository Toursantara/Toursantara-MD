package capstone.toursantara.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R

class CarouselPriceRangeAdapter(private val priceRanges: List<String>) :
    RecyclerView.Adapter<CarouselPriceRangeAdapter.ViewHolder>(), Carousel.Adapter {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btnPriceRange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_price_range, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = priceRanges[position]
    }

    override fun getItemCount(): Int {
        return priceRanges.size
    }

    override fun count(): Int {
        return priceRanges.size
    }

    override fun populate(view: View, index: Int) {
        // Inflate the layout if view is not already inflated
        val itemView = LayoutInflater.from(view.context).inflate(R.layout.item_price_range, null)
        val button: Button = itemView.findViewById(R.id.btnPriceRange)
        button.text = priceRanges[index]
    }

    override fun onNewItem(index: Int) {
        // Handle new item if needed
    }
}