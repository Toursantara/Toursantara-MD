package capstone.toursantara.app.view.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.toursantara.app.R
import capstone.toursantara.app.adapter.PlaceAdapter
import capstone.toursantara.app.model.Place
import com.google.android.material.button.MaterialButton

class BookmarksFragment : Fragment() {

    private lateinit var rvBookmarks: RecyclerView
    private lateinit var adapter: PlaceAdapter
    private lateinit var viewModel: BookmarksViewModel
    private lateinit var btnClearAll: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBookmarks = view.findViewById(R.id.rv_bookmarks)
        rvBookmarks.layoutManager = LinearLayoutManager(requireContext())
        adapter = PlaceAdapter()
        rvBookmarks.adapter = adapter

        btnClearAll = view.findViewById(R.id.btn_clear_all)
        btnClearAll.setOnClickListener {
            viewModel.deleteAllBookmarks()
        }

        viewModel = ViewModelProvider(requireActivity()).get(BookmarksViewModel::class.java)
        viewModel.bookmarks.observe(viewLifecycleOwner) { bookmarks ->
            bookmarks?.let {
                val places = bookmarks.map { tourismResult ->
                    Place(
                        place_name = tourismResult.placeName,
                        city = tourismResult.city,
                        rating = tourismResult.rating ?: "0.0", // Asumsi rating bisa null
                        category = tourismResult.category,
                        price = tourismResult.price?.toString() ?: "Rp -",
                        description = tourismResult.description,
                        lat = tourismResult.lat.toString(),
                        long = tourismResult.long.toString()
                    )
                }
                adapter.updatePlaces(places)
            }
        }
    }
}