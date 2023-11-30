package com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.repositories

import android.content.Context
import com.chungpingmak_chinnawutbooonluea_comp304lab5_ex1.models.Landmark
import org.json.JSONArray
import java.io.IOException

class LandmarkRepository(private val context: Context) {

    /**
     * Returns a list of landmarks.
     */
    fun getLandmarks(): List<Landmark> {
        val landmarks = mutableListOf<Landmark>()
        val jsonString = loadJSONFromAsset("landmarks.json")
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.getString("name")
            val type = jsonObject.getString("type")
            val description = jsonObject.getString("description")
            val latitude = jsonObject.getDouble("latitude")
            val longitude = jsonObject.getDouble("longitude")
            val image = jsonObject.getString("image")
            landmarks.add(Landmark(name, type, description, latitude, longitude, image))
        }

        return landmarks
    }

    /**
     * Loads a JSON file from the assets folder.
     */
    private fun loadJSONFromAsset(fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}
