package com.example.task.di

import android.content.Context
import androidx.room.Room
import com.example.task.database.AppDatabase
import com.example.task.database.DrinksDao
import com.example.task.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppConstants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDrinksDao(database: AppDatabase): DrinksDao {
        return database.recipeDao()
    }


}