package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.databinding.ItemLandmarkTypeBinding

class LandmarkTypeAdapter(
    private val types: List<LandmarkType>, // Use LandmarkType data class
    private val onClick: (LandmarkType) -> Unit
) : RecyclerView.Adapter<LandmarkTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLandmarkTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = types[position]
        holder.bind(type)
    }

    override fun getItemCount(): Int = types.size

    inner class ViewHolder(private val binding: ItemLandmarkTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(landmarkType: LandmarkType) {
            binding.typeName.text = landmarkType.name
            binding.landmarkTypeImage.setImageResource(landmarkType.imageResId)
            binding.root.setOnClickListener { onClick(landmarkType) }
        }
    }
}
