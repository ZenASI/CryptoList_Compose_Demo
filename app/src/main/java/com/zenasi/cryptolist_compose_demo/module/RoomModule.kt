package com.zenasi.cryptolist_compose_demo.module

import android.content.Context
import androidx.room.Room
import com.zenasi.cryptolist_compose_demo.utils.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomDB::class.java, "watch_list.db").build()

    @Singleton
    @Provides
    fun provideRoomDAO(db: RoomDB) = db.getRoomDAO()
}