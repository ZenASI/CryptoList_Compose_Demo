package com.zenasi.cryptolist_compose_demo.utils.room

import com.zenasi.cryptolist_compose_demo.model.AssetEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomUtils @Inject constructor(private val dao: RoomDAO) {
    private val TAG = this::class.java.simpleName

    suspend fun insertAsset(assetEntity: AssetEntity) = dao.insertItem(assetEntity)

    suspend fun removeAsset(assetEntity: AssetEntity) = dao.removeItem(assetEntity)

    suspend fun queryAllAsset() = dao.findAll()

    suspend fun queryAsset(id: String) = dao.queryItem(id)
}