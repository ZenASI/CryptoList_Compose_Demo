package com.zenasi.cryptolist_compose_demo.module

import android.content.Context
import com.zenasi.cryptolist_compose_demo.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideApplication(@ApplicationContext context: Context) = context as App
}