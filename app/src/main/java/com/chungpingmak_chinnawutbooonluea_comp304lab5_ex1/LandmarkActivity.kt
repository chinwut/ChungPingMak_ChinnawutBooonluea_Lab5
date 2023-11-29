package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ActivityLandmarkBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

        binding.satelliteButton.setOnClickListener {
            map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }

        binding.hybridButton.setOnClickListener {
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun displayLandmarkDetails() {
        landmark?.let {
            binding.landmarkName.text = it.name
            binding.landmarkDescription.text = it.description

            val imageResId = resources.getIdentifier( it.image.split(".")[0], "drawable", packageName)
            binding.landmarkImage.setImageResource(imageResId)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setLandmarkLocation()
    }

    private fun setLandmarkLocation() {
        landmark?.let {
            val location = LatLng(it.latitude, it.longitude)
            map?.addMarker(MarkerOptions().position(location).title(it.name))
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }
}