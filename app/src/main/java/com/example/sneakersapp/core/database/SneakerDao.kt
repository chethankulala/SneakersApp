package com.example.sneakersapp.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sneakersapp.features.model.Sneaker

@Dao
interface SneakerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSneaker(sneaker: Sneaker): Long

    @Query("SELECT * FROM saveSneakersData")
    fun getAllSneakersDetails(): List<Sneaker>

    @Query("DELETE from saveSneakersData WHERE id == :id")
    fun deleteSneakerItem(id: String): Int

}