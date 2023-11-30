package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Landmark(
    val name: String,
    val type: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: String
) : Parcelable