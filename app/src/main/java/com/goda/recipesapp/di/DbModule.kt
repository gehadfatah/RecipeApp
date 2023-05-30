package com.goda.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.goda.recipesapp.data.db.FoodDao
import com.goda.recipesapp.data.db.FoodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun provideChannelDao(appDatabase: FoodDatabase): FoodDao {
        return appDatabase.getFoodDao()
    }
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): FoodDatabase {
        return Room.databaseBuilder(
            appContext,
            FoodDatabase::class.java,
            "food_db.db"
        ).build()
    }
}