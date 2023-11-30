package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.models.Landmark
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.R
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ActivityLandmarkBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import android.content.pm.PackageManager
import android.widget.Toast
import com.google.android.libraries.places.api.model.RectangularBounds

//Student Name: Chinnawut Booonluea & Chung Ping Mak
//Student ID: 301276464 & 301281670
class LandmarkActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLandmarkBinding
    private var map: GoogleMap? = null
    private var landmark: Landmark? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        landmark = intent.getParcelableExtra("LANDMARK")

        setupMapFragment()
        displayLandmarkDetails()
        setupPlace()

        // default map type is normal
        binding.normalButton.isEnabled = false
        binding.satelliteButton.isEnabled = true
        binding.hybridButton.isEnabled = true

        binding.normalButton.setOnClickListener {
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL
            binding.normalButton.isEnabled = false
            binding.satelliteButton.isEnabled = true
            binding.hybridButton.isEnabled = true
        }

        binding.satelliteButton.setOnClickListener {
            map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            binding.normalButton.isEnabled = true
            binding.satelliteButton.isEnabled = false
            binding.hybridButton.isEnabled = true
        }

        binding.hybridButton.setOnClickListener {
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID
            binding.normalButton.isEnabled = true
            binding.satelliteButton.isEnabled = true
            binding.hybridButton.isEnabled = false
        }

        binding.placeButton.setOnClickListener {
            searchPlace()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Setup the map fragment
     */
    private fun setupMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Display the details of the landmark
     */
    private fun displayLandmarkDetails() {
        landmark?.let {
            binding.landmarkName.text = it.name
            binding.landmarkDescription.text = it.description

            val imageResId =
                resources.getIdentifier(it.image.split(".")[0], "drawable", packageName)
            binding.landmarkImage.setImageResource(imageResId)
        }
    }

    /**
     * Callback when the map is ready to be used
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setLandmarkLocation()
    }

    /**
     * Set the location of the landmark on the map
     */
    private fun setLandmarkLocation() {
        landmark?.let {
            val location = LatLng(it.latitude, it.longitude)
            map?.addMarker(MarkerOptions().position(location).title(it.name))
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }

    /**
     * Get the API key from the manifest file
     */
    private fun getMapsApiKey(): String? {
        return try {
            val applicationInfo =
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            applicationInfo.metaData.getString("com.google.android.geo.API_KEY")
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.api_not_found), Toast.LENGTH_SHORT).show()
            null
        }
    }

    /**
     * Setup the Place API
     */
    private fun setupPlace() {
        val apiKey = getMapsApiKey()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
    }

    /**
     * Call Place API to search for restaurants nearby the current location
     */
    private fun searchPlace() {
        val currentBounds = map!!.projection.visibleRegion.latLngBounds
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(getString(R.string.restaurant))
            .setLocationBias(RectangularBounds.newInstance(currentBounds))
            .build()
        val placesClient = Places.createClient(this)

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            Toast.makeText(this, "Total Restaurants found ${response.autocompletePredictions.size}", Toast.LENGTH_SHORT).show()
            for (prediction in response.autocompletePredictions) {
                val placeId = prediction.placeId

                val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

                val fetchPlaceRequest = FetchPlaceRequest.newInstance(placeId, placeFields)
                map?.clear()
                placesClient.fetchPlace(fetchPlaceRequest)
                    .addOnSuccessListener { fetchResponse ->
                        val place = fetchResponse.place
                        place.latLng?.let {
                            map?.addMarker(MarkerOptions().position(it).title(place.name))
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this,"$exception", Toast.LENGTH_SHORT).show()
                    }
            }
            map?.addMarker(MarkerOptions().position(LatLng(landmark!!.latitude, landmark!!.longitude)).title(landmark!!.name))
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "$exception", Toast.LENGTH_SHORT).show()
        }
    }
}