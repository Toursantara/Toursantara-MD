package capstone.toursantara.app.view.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import capstone.toursantara.app.R
import capstone.toursantara.app.database.TourismResult
import capstone.toursantara.app.databinding.ActivityDetailBinding
import capstone.toursantara.app.model.Place
import capstone.toursantara.app.view.bookmarks.BookmarksViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.slider.Slider
import kotlinx.coroutines.launch
import java.util.Locale

class DetailActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mMap: GoogleMap
    private lateinit var place: Place
    private lateinit var viewModel: BookmarksViewModel
    private var defaultColor: Int = 0
    private var isSaved = false

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            mMap.isMyLocationEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        place = intent.getParcelableExtra<Place>("place")!!
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = ViewModelProvider(this).get(BookmarksViewModel::class.java)


        binding.placeName.text = place.place_name
        binding.placeCity.text = place.city
        binding.placeRating.text = place.rating.toString()
        binding.placeDescription.text = place.description

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        place.place_name?.let {
            viewModel.isTourismResultSaved(it).observe(this, { saved ->
                isSaved = saved
                updateButtonColor()
            })
        }

        defaultColor = ContextCompat.getColor(this, R.color.default_button_color)

        binding.rateButton.setOnClickListener {
            showRatingDialog()
        }

        binding.saveIcon.setOnClickListener {
            lifecycleScope.launch {
                val tourismResult = convertPlaceToTourismResult(place)

                if (isSaved) {
                    viewModel.removeBookmark(tourismResult)
                } else {
                    if (place.place_name?.let { it1 -> viewModel.getTourismResultByNameSync(it1) } == null) {
                        viewModel.saveBookmark(tourismResult)
                    }
                }
                isSaved = !isSaved
                updateButtonColor()
            }
        }
    }

    private fun updateButtonColor() {
        val color = if (isSaved) ContextCompat.getColor(this, R.color.black) else defaultColor
        binding.saveIcon.setColorFilter(color)
    }

    private fun showRatingDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.rating_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)

        val slider = dialog.findViewById<Slider>(R.id.ratingSlider)
        val submitButton = dialog.findViewById<Button>(R.id.submitRatingButton)
        val ratingValueText = dialog.findViewById<TextView>(R.id.ratingValueText)

        slider.addOnChangeListener { _, value, _ ->
            ratingValueText.text = String.format("%.1f", value)
        }

        submitButton.setOnClickListener {
            // Simpan rating ke database atau lakukan aksi lain
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun convertPlaceToTourismResult(place: Place): TourismResult {
        return TourismResult(
            city = place.city ?: "",
            description = place.description ?: "",
            lat = place.lat?.toDouble() ?: 0.0,
            long = place.long?.toDouble() ?: 0.0,
            placeName = place.place_name ?: "",
            rating = place.rating?.toString() ?: "",
            category = place.category ?: "",
            price = place.price?.toDoubleOrNull() ?: 0.0,
            timestamp = System.currentTimeMillis()
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapUI()
        getMyPlace()
        setMapStyle()

        val latitude = place.lat!!.toDouble()
        val longitude = place.long!!.toDouble()
        val location = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(location).title(place.place_name))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        mMap.setOnMarkerClickListener(this)
    }

    private fun setupMapUI() {
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }

    private fun getMyPlace() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Failed when parsing style.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Something is wrong: ", exception)
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)
        val address = addresses?.get(0)?.getAddressLine(0)

        val gmmIntentUri = Uri.parse("google.navigation:q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
        return true
    }
}