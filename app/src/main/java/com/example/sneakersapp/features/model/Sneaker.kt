package com.example.sneakersapp.features.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saveSneakersData")
@Parcelize
data class Sneaker(
    @field:PrimaryKey(autoGenerate = false)
    val id: String,
    val brand: String,
    val colorway: String,
    val gender: String,
    val media: Media,
    val releaseDate: String,
    val retailPrice: Int,
    val styleId: String,
    val shoe: String,
    val name: String,
    val title: String,
    val year: Int,
    var size: Int = 0,
    var color: String? = null
) : Parcelable

@Parcelize
data class Media(
    val imageUrl: String,
    val smallImageUrl: String,
    val thumbUrl: String
) : Parcelable
