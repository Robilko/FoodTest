package com.example.foodtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodtest.data.db.dao.ProductsCacheDao
import com.example.foodtest.data.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductsDataBase : RoomDatabase() {

    abstract fun productsCacheDao(): ProductsCacheDao
}