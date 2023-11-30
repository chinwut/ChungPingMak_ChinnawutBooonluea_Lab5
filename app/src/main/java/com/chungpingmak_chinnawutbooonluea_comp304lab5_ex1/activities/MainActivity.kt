package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.models.LandmarkType
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.adapters.LandmarkTypeAdapter
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.R
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ActivityMainBinding

//Student Name: Chinnawut Booonluea & Chung Ping Mak
//Student ID: 301276464 & 301281670
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val landmarkTypes = listOf(
            LandmarkType(
                getString(R.string.old_building),
                R.drawable.casa_loma
            ),
            LandmarkType(
                getString(R.string.museum),
                R.drawable.art_gallery_of_ontario
            ),
            LandmarkType(
                getString(R.string.park_and_outdoor_spaces),
                R.drawable.toronto_islands
            ),
            LandmarkType(
                getString(R.string.modern_architecture),
                R.drawable.rogers_centre
            ),
            LandmarkType(
                getString(R.string.cultural_and_entertainment_venues),
                R.drawable.scotiabank_arena
            ),
            LandmarkType(
                getString(R.string.educational_institutions),
                R.drawable.university_of_toronto
            )
        )

        binding.landmarkTypeGrid.layoutManager = GridLayoutManager(this, 2)
        binding.landmarkTypeGrid.adapter = LandmarkTypeAdapter(landmarkTypes) { landmarkType ->
            val intent = Intent(this, LandmarkTypeActivity::class.java).apply {
                putExtra("TYPE", landmarkType.name)
            }
            startActivity(intent)
        }
    }
}