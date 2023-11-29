package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.R
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val landmarkTypes = listOf(
            LandmarkType("Old Buildings", R.drawable.casa_loma),
            LandmarkType("Museums", R.drawable.art_gallery_of_ontario),
            LandmarkType("Parks and Outdoor Spaces", R.drawable.toronto_islands),
            LandmarkType("Modern Architecture", R.drawable.rogers_centre),
            LandmarkType("Cultural and Entertainment Venues", R.drawable.scotiabank_arena),
            LandmarkType("Educational Institutions", R.drawable.university_of_toronto)
        )

        binding.landmarkTypeGrid.layoutManager = GridLayoutManager(this, 2)
        binding.landmarkTypeGrid.adapter = LandmarkTypeAdapter(landmarkTypes) { landmarkType ->
            val intent = Intent(this, LandmarkTypeActivity::class.java).apply {
                putExtra("TYPE", landmarkType.name)
                // If you need to pass the image ID, add it here
            }
            startActivity(intent)
        }
    }
}