package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.adapters.LandmarkAdapter
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ActivityLandmarkTypeBinding
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.repositories.LandmarkRepository

//Student Name: Chinnawut Booonluea & Chung Ping Mak
//Student ID: 301276464 & 301281670
class LandmarkTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandmarkTypeBinding
    private lateinit var landmarkRepository: LandmarkRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        landmarkRepository = LandmarkRepository(this)

        val type = intent.getStringExtra("TYPE")
        val landmarks = landmarkRepository.getLandmarks().filter { it.type == type }
        binding.toolbar.title = type
        binding.landmarkList.layoutManager = LinearLayoutManager(this)
        binding.landmarkList.adapter = LandmarkAdapter(landmarks) { landmark ->
            val intent = Intent(this, LandmarkActivity::class.java).apply {
                putExtra("LANDMARK", landmark)
            }
            startActivity(intent)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
