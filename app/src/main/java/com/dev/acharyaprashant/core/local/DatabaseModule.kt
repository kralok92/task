package com.dev.acharyaprashant.core.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
   fun providesMediaDatabase(@ApplicationContext appContext: Context): MediaDatabase {
        return Room.databaseBuilder(
            appContext,
            MediaDatabase::class.java,
            "acharyaprashant"
        ).build()
   }

}