package com.example.sneakersapp

import android.app.Application
import com.example.sneakersapp.core.di.AppComponent
import com.example.sneakersapp.core.di.AppModule
import com.example.sneakersapp.core.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}