package com.example.sneakersapp.features.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sneakersapp.core.database.SneakerDao
import com.example.sneakersapp.features.model.Sneaker
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.charset.Charset

class DataRepository (private val context: Context, private val sneakerDao: SneakerDao) {

    // Flow to emit the list of sneakers
    /*val sneakersFlow: Flow<List<Sneaker>> = flow {
        try {
            val jsonString = loadJSONFromAsset("sneakers.json")
            val sneakers = Gson().fromJson(jsonString, Array<Sneaker>::class.java).toList()
            emit(sneakers)
        } catch (e: Exception) {
            // Handle exception (e.g., file not found, JSON parsing error)
        }
    }.flowOn(Dispatchers.IO)*/

    fun getSneakersDetails(): List<Sneaker> {
        var sneakers: List<Sneaker> = listOf()
        try {
            val jsonString = loadJSONFromAsset("sneakers.json")
            sneakers = Gson().fromJson(jsonString, Array<Sneaker>::class.java).toList()
        } catch (e: Exception) {
            // Handle exception (e.g., file not found, JSON parsing error)
            Log.d("hello", "file not found")
        }
        return sneakers
    }

    suspend fun addSneakersDetailsToCart(sneaker: Sneaker) = sneakerDao.insertSneaker(sneaker)

    fun getSneakersAddedToCart() = sneakerDao.getAllSneakersDetails()

    fun deleteSneakerItem(id: String) = sneakerDao.deleteSneakerItem(id)

    private fun loadJSONFromAsset(filename: String): String {
        return try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            // Handle exception
            ""
        }
    }
}