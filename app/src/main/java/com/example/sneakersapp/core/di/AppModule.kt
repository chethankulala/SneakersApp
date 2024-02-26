package com.example.sneakersapp.core.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.core.database.SneakerDao
import com.example.sneakersapp.core.database.SneakerDatabase
import com.example.sneakersapp.features.repository.DataRepository
import com.example.sneakersapp.features.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSneakerDao(context: Context): SneakerDao {
        return SneakerDatabase.getDatabase(application).getSneakerDao()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(context: Context,sneakerDao: SneakerDao): DataRepository {
        return DataRepository(context, sneakerDao)
    }

    @Provides
    fun provideViewModelFactory(repository: DataRepository): ViewModelProvider.Factory {
        return ViewModelFactory(repository)
    }

}