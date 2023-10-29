package com.zenasi.cryptolist_compose_demo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zenasi.cryptolist_compose_demo.model.AssetEntity.Companion.TABLE_NAME

/**
 * a simple assBean
 */
@Entity(tableName = TABLE_NAME)
data class AssetEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT) val name: String = "",
    @ColumnInfo(name = "rank", typeAffinity = ColumnInfo.TEXT) val rank: String = "",
    @ColumnInfo(name = "symbol", typeAffinity = ColumnInfo.TEXT) val symbol: String = "",
) {
    companion object {
        const val TABLE_NAME = "AssetEntity"
    }

    fun getFakeAsset(): AssetEntity {
        return AssetEntity(
            id = "tether",
            rank = "3",
            symbol = "USDT",
            name = "Tether",
        )
    }
}