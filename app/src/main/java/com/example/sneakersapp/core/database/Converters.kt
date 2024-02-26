package com.example.sneakersapp.core.database

import androidx.room.TypeConverter
import com.example.sneakersapp.features.model.Media
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun mediaToString(media: Media?): String? {
        return gson.toJson(media)
    }

    @TypeConverter
    fun stringToMedia(value: String?): Media? {
        val type = object : TypeToken<Media>() {}.type
        return gson.fromJson(value, type)
    }

}