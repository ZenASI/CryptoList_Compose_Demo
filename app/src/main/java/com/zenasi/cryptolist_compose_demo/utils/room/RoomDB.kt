package com.zenasi.cryptolist_compose_demo.utils.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zenasi.cryptolist_compose_demo.model.AssetEntity

@Database(entities = [AssetEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getRoomDAO(): RoomDAO
}