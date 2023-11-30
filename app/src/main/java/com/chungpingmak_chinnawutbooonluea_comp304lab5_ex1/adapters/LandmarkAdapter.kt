package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.models.Landmark
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ItemLandmarkBinding

class LandmarkAdapter(
    private val landmarks: List<Landmark>,
    private val onItemClick: (Landmark) -> Unit
) : RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkViewHolder {
        val binding =
            ItemLandmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LandmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        val landmark = landmarks[position]
        holder.bind(landmark)
    }

    override fun getItemCount(): Int = landmarks.size

    inner class LandmarkViewHolder(private val binding: ItemLandmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(landmark: Landmark) {
            binding.landmarkName.text = landmark.name
            binding.landmarkDescription.text = landmark.description

            val imageResId = itemView.context.resources.getIdentifier(
                landmark.image.split(".")[0], // removing the file extension
                "drawable",
                itemView.context.packageName
            )
            if (imageResId != 0) {
                binding.landmarkImage.setImageResource(imageResId)
            }
            binding.root.setOnClickListener { onItemClick(landmark) }
        }
    }
}
