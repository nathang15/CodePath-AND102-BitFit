package com.example.bitfit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<Food>>

    @Insert
    fun insert(food: Food)

    @Delete
    fun deleteItem(food: Food)

    @Query("DELETE FROM food_table")
    fun deleteAll()

    @Query("SELECT calories FROM food_table")
    fun getCal(): List<String>

}