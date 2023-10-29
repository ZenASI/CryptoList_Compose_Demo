package com.zenasi.cryptolist_compose_demo.utils.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zenasi.cryptolist_compose_demo.model.AssetEntity

@Dao
interface RoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: AssetEntity): Long

    @Delete
    suspend fun removeItem(item: AssetEntity)

    @Query("SELECT * FROM ${AssetEntity.TABLE_NAME}")
    suspend fun findAll(): List<AssetEntity>

    @Query("SELECT * FROM ${AssetEntity.TABLE_NAME} WHERE id LIKE :id")
    suspend fun queryItem(id: String): AssetEntity
}